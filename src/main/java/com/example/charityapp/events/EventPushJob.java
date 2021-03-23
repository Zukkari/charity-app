package com.example.charityapp.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Set;

@Component
public class EventPushJob implements Runnable {
  private static final Logger log = LoggerFactory.getLogger(EventPushJob.class);

  private final ObjectMapper mapper;
  private final EventEmitter eventEmitter;

  public EventPushJob(ObjectMapper mapper, EventEmitter eventEmitter) {
    this.mapper = mapper;
    this.eventEmitter = eventEmitter;
  }

  @Override
  public void run() {
    log.trace("Pushing async events");

    var queue = eventEmitter.getDeque();
    while (true) {
      var event = queue.poll();
      if (event == null) {
        break;
      }

      log.trace("Pushing event: {}", event);
      push(event);
    }

    log.trace("Finished pushing events");
  }

  public void push(ProductEvent event) {
    try {
      var serializedEvent = mapper.writeValueAsString(event);
      var sseEvent = SseEmitter.event().data(serializedEvent, MediaType.APPLICATION_JSON).build();
      for (SseEmitter emitter : eventEmitter.getEmitters()) {
        push(sseEvent, emitter);
      }
    } catch (IOException e) {
      throw new UncheckedIOException("Failed to serialize event: " + event, e);
    }
  }

  private void push(Set<ResponseBodyEmitter.DataWithMediaType> sseEvent, SseEmitter emitter) {
    try {
      emitter.send(sseEvent);
    } catch (Exception e) {
      log.trace("Failed to deliver event", e);
    }
  }
}

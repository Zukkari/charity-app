package com.example.charityapp.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.UncheckedIOException;

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

    var emitters = eventEmitter.getEmitters();
    log.trace("Emitters before clean up: {}", emitters.size());

    for (SseEmitter sseEmitter : eventEmitter.getForRemoval()) {
      emitters.remove(sseEmitter);
    }

    log.trace("Finished clean up of emitters, new size: {}", emitters.size());

    var queue = eventEmitter.getDeque();
    while (true) {
      var event = queue.poll();
      if (event == null) {
        break;
      }

      push(event);
    }
  }

  public void push(ProductEvent event) {
    try {
      var serializedEvent = mapper.writeValueAsString(event);
      var sseEvent = SseEmitter.event().data(serializedEvent, MediaType.APPLICATION_JSON).build();

      for (SseEmitter emitter : eventEmitter.getEmitters()) {
        emitter.send(sseEvent);
      }
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}

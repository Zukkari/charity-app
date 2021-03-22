package com.example.charityapp.events.implmentation;

import com.example.charityapp.events.EventEmitter;
import com.example.charityapp.events.ProductEvent;
import com.example.charityapp.events.ProductEventKind;
import com.example.charityapp.model.ProductLineItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class EventEmitterImpl implements EventEmitter {
  private static final Logger log = LoggerFactory.getLogger(EventEmitterImpl.class);

  private final ObjectMapper mapper;
  private final Set<SseEmitter> emitters = new HashSet<>();

  @Autowired
  public EventEmitterImpl(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Async
  public void publishItemBookedEvent(ProductLineItem item) {
    log.info("Publishing item booked event for item with id: {}", item);

    var domainEvent = new ProductEvent(item.getProduct().getId(), ProductEventKind.BOOKED);
    push(domainEvent);
  }

  @Async
  public void publishItemReleasedEvent(ProductLineItem item) {
    log.info("Publishing item released event for item with id: {}", item);

    var domainEvent = new ProductEvent(item.getProduct().getId(), ProductEventKind.RELEASED);
    push(domainEvent);
  }

  @Override
  public void register(SseEmitter emitter) {
    emitter.onCompletion(() -> emitters.remove(emitter));
    emitters.add(emitter);
  }

  public void push(ProductEvent event) {
    try {
      var serializedEvent = mapper.writeValueAsString(event);
      var sseEvent = SseEmitter.event().data(serializedEvent, MediaType.APPLICATION_JSON).build();

      for (SseEmitter emitter : emitters) {
        emitter.send(sseEvent);
      }
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}

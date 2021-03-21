package com.example.charityapp.events.implmentation;

import com.example.charityapp.events.EventEmitter;
import com.example.charityapp.model.ProductLineItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.UUID;

@Component
public class EventEmitterImpl implements EventEmitter {
  private static final Logger log = LoggerFactory.getLogger(EventEmitterImpl.class);

  private final SseEmitter emitter;

  @Autowired
  public EventEmitterImpl(SseEmitter emitter) {
    this.emitter = emitter;
  }

  @Async
  public void publishItemBookedEvent(ProductLineItem item) {
    log.info("Publishing item booked event for item with id: {}", item);

    var eventId = UUID.randomUUID().toString();

    var event = SseEmitter.event().id(eventId).name("item-booked").data(item.getId()).build();

    try {
      emitter.send(event);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  @Async
  public void publishItemReleasedEvent(ProductLineItem item) {
    log.info("Publishing item released event for item with id: {}", item);

    var eventId = UUID.randomUUID().toString();

    var event = SseEmitter.event().id(eventId).name("item-released").data(item.getId()).build();

    try {
      emitter.send(event);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}

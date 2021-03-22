package com.example.charityapp.events.implmentation;

import com.example.charityapp.events.EventEmitter;
import com.example.charityapp.events.ProductEvent;
import com.example.charityapp.events.ProductEventKind;
import com.example.charityapp.model.ProductLineItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class EventEmitterImpl implements EventEmitter {
  private static final Logger log = LoggerFactory.getLogger(EventEmitterImpl.class);

  private final Set<SseEmitter> emitters = new HashSet<>();

  private final Deque<ProductEvent> deque = new ArrayDeque<>(1000);

  private final List<SseEmitter> forRemoval = new CopyOnWriteArrayList<>();

  @Override
  public void publishItemBookedEvent(ProductLineItem item, long newQuantity) {
    log.info("Publishing item booked event for item with id: {}", item);

    var domainEvent =
        new ProductEvent(item.getProduct().getId(), ProductEventKind.BOOKED, newQuantity);
    deque.push(domainEvent);
  }

  @Override
  public void publishItemReleasedEvent(ProductLineItem item, long newQuantity) {
    log.info("Publishing item released event for item with id: {}", item);

    var domainEvent =
        new ProductEvent(item.getProduct().getId(), ProductEventKind.RELEASED, newQuantity);
    deque.push(domainEvent);
  }

  @Override
  public void register(SseEmitter emitter) {
    emitter.onCompletion(() -> forRemoval.add(emitter));
    emitters.add(emitter);
  }

  @Override
  public Set<SseEmitter> getEmitters() {
    return emitters;
  }

  @Override
  public Deque<ProductEvent> getDeque() {
    return deque;
  }

  @Override
  public List<SseEmitter> getForRemoval() {
    return forRemoval;
  }
}

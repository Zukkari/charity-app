package com.example.charityapp.events;

import com.example.charityapp.model.ProductLineItem;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Collection;
import java.util.Deque;

/**
 * Simple event publisher interface. Such approach is quite error prone, so it would be better to
 * use database transaction mining or some other event publishing mechanism. But for our simple
 * application this will suffice.
 */
public interface EventEmitter {

  /**
   * Publish event indicating that provided item has been successfully booked
   *
   * @param item item that was booked
   * @param newQuantity new quantity of the product
   */
  void publishItemBookedEvent(ProductLineItem item, long newQuantity);

  /**
   * Publish event that this item has been released from booking
   *
   * @param item item that was released from booking
   * @param newQuantity new quantity of the product
   */
  void publishItemReleasedEvent(ProductLineItem item, long newQuantity);

  /**
   * Register this emitter to receive events
   *
   * @param emitter to register
   */
  void register(SseEmitter emitter);

  Collection<SseEmitter> getEmitters();

  Deque<ProductEvent> getDeque();
}

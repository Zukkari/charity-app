package com.example.charityapp.events;

import com.example.charityapp.model.ProductLineItem;

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
   */
  void publishItemBookedEvent(ProductLineItem item);

  /**
   * Publish event that this item has been released from booking
   *
   * @param item item that was released from booking
   */
  void publishItemReleasedEvent(ProductLineItem item);
}

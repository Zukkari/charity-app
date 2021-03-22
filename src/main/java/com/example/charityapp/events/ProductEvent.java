package com.example.charityapp.events;

public class ProductEvent {
  private final long productId;
  private final ProductEventKind eventKind;

  public ProductEvent(long productId, ProductEventKind eventKind) {
    this.productId = productId;
    this.eventKind = eventKind;
  }

  public long getProductId() {
    return productId;
  }

  public ProductEventKind getEventKind() {
    return eventKind;
  }
}

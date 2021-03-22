package com.example.charityapp.events;

public class ProductEvent {
  private final long productId;
  private final ProductEventKind eventKind;
  private final long newQuantity;

  public ProductEvent(long productId, ProductEventKind eventKind, long newQuantity) {
    this.productId = productId;
    this.eventKind = eventKind;
    this.newQuantity = newQuantity;
  }

  public long getProductId() {
    return productId;
  }

  public ProductEventKind getEventKind() {
    return eventKind;
  }

  public long getNewQuantity() {
    return newQuantity;
  }
}

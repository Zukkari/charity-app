package com.example.charityapp.exceptions;

public class IllegalOrderStateException extends RuntimeException {
  private final long orderId;

  public IllegalOrderStateException(long orderId) {
    this.orderId = orderId;
  }

  public long getOrderId() {
    return orderId;
  }
}

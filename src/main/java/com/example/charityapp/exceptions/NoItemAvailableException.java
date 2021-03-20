package com.example.charityapp.exceptions;

public class NoItemAvailableException extends RuntimeException {
  private final long productId;

  public NoItemAvailableException(long productId) {
    this.productId = productId;
  }

  public long getProductId() {
    return productId;
  }
}

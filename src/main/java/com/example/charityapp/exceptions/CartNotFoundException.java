package com.example.charityapp.exceptions;

public class CartNotFoundException extends RuntimeException {
  private final long providedId;

  public CartNotFoundException(long providedId) {
    this.providedId = providedId;
  }

  public long getProvidedId() {
    return providedId;
  }
}

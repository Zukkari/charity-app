package com.example.charityapp.exceptions;

import java.math.BigDecimal;

public class InsufficientAmountException extends RuntimeException {
  private final long cartId;
  private final BigDecimal toPay;
  private final BigDecimal offered;

  public InsufficientAmountException(long cartId, BigDecimal toPay, BigDecimal offered) {
    this.cartId = cartId;
    this.toPay = toPay;
    this.offered = offered;
  }

  public long getCartId() {
    return cartId;
  }

  public BigDecimal getToPay() {
    return toPay;
  }

  public BigDecimal getOffered() {
    return offered;
  }
}

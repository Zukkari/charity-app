package com.example.charityapp.exceptions;

import java.math.BigDecimal;

public class InsufficientAmountException extends RuntimeException {
  private final long orderId;
  private final BigDecimal toPay;
  private final BigDecimal offered;

  public InsufficientAmountException(long orderId, BigDecimal toPay, BigDecimal offered) {
    this.orderId = orderId;
    this.toPay = toPay;
    this.offered = offered;
  }

  public long getOrderId() {
    return orderId;
  }

  public BigDecimal getToPay() {
    return toPay;
  }

  public BigDecimal getOffered() {
    return offered;
  }
}

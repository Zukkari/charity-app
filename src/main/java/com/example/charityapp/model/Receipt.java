package com.example.charityapp.model;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Embeddable
public class Receipt {

  private BigDecimal paidAmount;

  private LocalDateTime paidTime;

  public Receipt() {}

  public Receipt(BigDecimal paidAmount) {
    this.paidAmount = paidAmount;
    this.paidTime = LocalDateTime.now();
  }

  public BigDecimal getPaidAmount() {
    return paidAmount;
  }

  public void setPaidAmount(BigDecimal paidAmount) {
    this.paidAmount = paidAmount;
  }

  public LocalDateTime getPaidTime() {
    return paidTime;
  }

  public void setPaidTime(LocalDateTime paidTime) {
    this.paidTime = paidTime;
  }
}

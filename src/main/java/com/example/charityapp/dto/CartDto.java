package com.example.charityapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "Cart", description = "Entity representing the cart")
public class CartDto {
  private long id;
  private List<ProductLineItemDto> items;

  private BigDecimal total;
  private BigDecimal paidAmount;
  private LocalDateTime paidTime;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<ProductLineItemDto> getItems() {
    return items;
  }

  public void setItems(List<ProductLineItemDto> items) {
    this.items = items;
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

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }
}

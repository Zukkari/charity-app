package com.example.charityapp.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ProductLineItem {

  @Id private long id;

  @ManyToOne private Product product;

  @Enumerated(EnumType.STRING)
  private LineItemStatus lineItemStatus;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public LineItemStatus getLineItemStatus() {
    return lineItemStatus;
  }

  public void setLineItemStatus(LineItemStatus lineItemStatus) {
    this.lineItemStatus = lineItemStatus;
  }
}

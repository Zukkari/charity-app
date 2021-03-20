package com.example.charityapp.dto;

import com.example.charityapp.model.LineItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Schema(name = "Product line item", description = "Entity representing product line item")
public class ProductLineItemDto {

  private long id;

  @NotNull @Positive private long productId;

  private String productName;

  private BigDecimal price;

  private LineItemStatus itemStatus;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public LineItemStatus getItemStatus() {
    return itemStatus;
  }

  public void setItemStatus(LineItemStatus itemStatus) {
    this.itemStatus = itemStatus;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public long getProductId() {
    return productId;
  }

  public void setProductId(long productId) {
    this.productId = productId;
  }
}

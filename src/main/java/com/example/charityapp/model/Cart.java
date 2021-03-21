package com.example.charityapp.model;

import com.example.charityapp.exceptions.InsufficientAmountException;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

  @Id @GeneratedValue private long id;

  private LocalDateTime created;

  private LocalDateTime modified;

  @OneToMany private List<ProductLineItem> items;

  @Embedded private Receipt receipt;

  public void addItem(ProductLineItem item) {
    if (items == null) {
      items = new ArrayList<>();
    }

    items.add(item);
    modified = LocalDateTime.now();
  }

  /**
   * Pay for this order with amount of money provided
   *
   * @param amount amount of money to pay for this order
   * @return amount of money to return to the customer after order has been paid
   * @throws InsufficientAmountException - thrown if money amount offered is not enough to pay for
   *     an order
   */
  public BigDecimal acceptOrder(BigDecimal amount) {
    var toPay =
        items.stream()
            .map(ProductLineItem::getProduct)
            .map(Product::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    if (amount.compareTo(toPay) < 0) {
      throw new InsufficientAmountException(id, toPay, amount);
    }

    this.receipt = new Receipt(toPay, amount);
    this.modified = LocalDateTime.now();

    return amount.subtract(toPay);
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public LocalDateTime getModified() {
    return modified;
  }

  public void setModified(LocalDateTime modified) {
    this.modified = modified;
  }

  public List<ProductLineItem> getItems() {
    return items;
  }

  public void setItems(List<ProductLineItem> items) {
    this.items = items;
  }

  public Receipt getReceipt() {
    return receipt;
  }

  public void setReceipt(Receipt receipt) {
    this.receipt = receipt;
  }
}

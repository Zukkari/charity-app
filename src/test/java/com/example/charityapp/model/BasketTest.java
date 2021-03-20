package com.example.charityapp.model;

import com.example.charityapp.exceptions.InsufficientAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BasketTest {

  private Basket basket;

  @BeforeEach
  void setUp() {
    basket = new Basket();
    var basketLineItems = new ArrayList<ProductLineItem>();

    var product = new Product();
    product.setId(1);
    product.setName("Best product");
    product.setPrice(BigDecimal.TEN);

    var item = new ProductLineItem();

    item.setId(1);
    item.setProduct(product);

    basket.setItems(basketLineItems);
    basket.addItem(item);
  }

  @Test
  void test_accept_ok_amount() {
    var now = LocalDateTime.now();

    var remainder = basket.acceptOrder(BigDecimal.TEN);
    var receipt = basket.getReceipt();

    assertThat(remainder).isEqualTo(BigDecimal.ZERO);
    assertThat(receipt.getPaidAmount()).isEqualTo(BigDecimal.TEN);
    assertThat(receipt.getPaidTime()).isAfter(now);
  }

  @Test
  void test_bad_amount() {
    assertThrows(InsufficientAmountException.class, () -> basket.acceptOrder(BigDecimal.ZERO));
  }

  @Test
  void test_add_item() {
    var modified = basket.getModified();

    var item = basket.getItems().get(0);

    basket.addItem(item);

    var newModified = basket.getModified();

    assertThat(newModified).isAfterOrEqualTo(modified);
  }
}

package com.example.charityapp.model;

import com.example.charityapp.exceptions.InsufficientAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CartTest {

  private Cart cart;

  @BeforeEach
  void setUp() {
    cart = new Cart();
    var basketLineItems = new ArrayList<ProductLineItem>();

    var product = new Product();
    product.setId(1);
    product.setName("Best product");
    product.setPrice(BigDecimal.TEN);

    var item = new ProductLineItem();

    item.setId(1);
    item.setProduct(product);

    cart.setItems(basketLineItems);
    cart.addItem(item);
  }

  @Test
  void test_accept_ok_amount() {
    var now = LocalDateTime.now();

    var remainder = cart.acceptOrder(BigDecimal.TEN);
    var receipt = cart.getReceipt();

    assertThat(remainder).isEqualTo(BigDecimal.ZERO);
    assertThat(receipt.getPaidAmount()).isEqualTo(BigDecimal.TEN);
    assertThat(receipt.getPaidTime()).isAfterOrEqualTo(now);
  }

  @Test
  void test_bad_amount() {
    assertThrows(InsufficientAmountException.class, () -> cart.acceptOrder(BigDecimal.ZERO));
  }

  @Test
  void test_add_item() {
    var modified = cart.getModified();

    var item = cart.getItems().get(0);

    cart.addItem(item);

    var newModified = cart.getModified();

    assertThat(newModified).isAfterOrEqualTo(modified);
  }
}

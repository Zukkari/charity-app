package com.example.charityapp.dto;

import com.example.charityapp.config.ModelMapperConfig;
import com.example.charityapp.model.Cart;
import com.example.charityapp.model.LineItemStatus;
import com.example.charityapp.model.Product;
import com.example.charityapp.model.ProductLineItem;
import com.example.charityapp.model.Receipt;
import com.example.charityapp.module.CharityModelMapperModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class CartDtoTest {

  private ModelMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new ModelMapperConfig().modelMapper(new CharityModelMapperModule());
  }

  @Test
  void test_conversion() {
    var cart = new Cart();
    cart.setId(2);
    cart.setCreated(LocalDateTime.now());
    cart.setModified(LocalDateTime.now());

    var items = new ArrayList<ProductLineItem>();
    var item = new ProductLineItem();
    var product = new Product();
    product.setId(4);
    product.setName("Big product");
    product.setPrice(BigDecimal.TEN);

    item.setProduct(product);
    item.setLineItemStatus(LineItemStatus.OPEN);
    item.setId(5);

    items.add(item);

    cart.setItems(items);

    var receipt = new Receipt(BigDecimal.TEN, BigDecimal.TEN);
    cart.setReceipt(receipt);

    var dto = mapper.map(cart, CartDto.class);

    assertThat(dto.getId()).isEqualTo(2);
    assertThat(dto.getPaidAmount()).isEqualTo(BigDecimal.TEN);
    assertThat(dto.getPaidTime()).isEqualTo(receipt.getPaidTime());
    assertThat(dto.getItems()).isNotEmpty();
  }
}

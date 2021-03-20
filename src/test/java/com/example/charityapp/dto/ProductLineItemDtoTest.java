package com.example.charityapp.dto;

import com.example.charityapp.config.ModelMapperConfig;
import com.example.charityapp.model.LineItemStatus;
import com.example.charityapp.model.Product;
import com.example.charityapp.model.ProductLineItem;
import com.example.charityapp.module.CharityModelMapperModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductLineItemDtoTest {

  private ModelMapper mapper;

  @BeforeEach
  void setUp() {
    this.mapper = new ModelMapperConfig().modelMapper(new CharityModelMapperModule());
  }

  @Test
  void test_conversion() {
    var item = new ProductLineItem();
    item.setId(1);

    var product = new Product();
    product.setId(2);
    product.setName("Big product");
    product.setPrice(BigDecimal.TEN);

    item.setProduct(product);
    item.setLineItemStatus(LineItemStatus.OPEN);

    var dto = mapper.map(item, ProductLineItemDto.class);

    assertThat(dto.getId()).isEqualTo(1);
    assertThat(dto.getItemStatus()).isEqualTo(LineItemStatus.OPEN);
    assertThat(dto.getProductName()).isEqualTo("Big product");
    assertThat(dto.getProductId()).isEqualTo(2L);
  }
}

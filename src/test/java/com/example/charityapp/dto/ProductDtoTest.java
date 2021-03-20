package com.example.charityapp.dto;

import com.example.charityapp.config.ModelMapperConfig;
import com.example.charityapp.model.Product;
import com.example.charityapp.module.CharityModelMapperModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProductDtoTest {

  private ModelMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new ModelMapperConfig().modelMapper(new CharityModelMapperModule());
  }

  @Test
  void test_conversion() {
    var product = new Product();
    product.setPrice(BigDecimal.TEN);
    product.setName("Test product");
    product.setId(1);

    var dto = mapper.map(product, ProductDto.class);

    assertThat(dto.getProductId()).isEqualTo(1);
    assertThat(dto.getName()).isEqualTo("Test product");
    assertThat(dto.getPrice()).isEqualTo(BigDecimal.TEN);
  }
}

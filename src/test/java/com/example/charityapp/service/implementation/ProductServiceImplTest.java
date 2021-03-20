package com.example.charityapp.service.implementation;

import com.example.charityapp.model.Product;
import com.example.charityapp.repository.ProductLineItemRepository;
import com.example.charityapp.repository.ProductRepository;
import com.example.charityapp.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class ProductServiceImplTest {

  private ProductLineItemRepository productLineItemRepository;
  private ProductRepository productRepository;

  private ProductService productService;

  @BeforeEach
  void setUp() {
    this.productLineItemRepository = mock(ProductLineItemRepository.class);
    this.productRepository = mock(ProductRepository.class);

    this.productService =
        new ProductServiceImpl(productLineItemRepository, productRepository, new ModelMapper());
  }

  @Test
  void test_getAllProducts() {
    var items = List.of(new Product(), new Product());

    given(productRepository.findAll()).willReturn(items);
    given(productLineItemRepository.countByProductId(anyLong())).willReturn(10L);

    var products = productService.getAllProducts();

    then(productRepository).should().findAll();
    then(productLineItemRepository).should(times(2)).countByProductId(anyLong());

    assertThat(products).isNotEmpty().allMatch(product -> product.getQuantity() == 10);
  }
}

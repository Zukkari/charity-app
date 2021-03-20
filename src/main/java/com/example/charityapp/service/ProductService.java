package com.example.charityapp.service;

import com.example.charityapp.dto.ProductDto;

import java.util.List;

public interface ProductService {

  /**
   * Get all products that are supported by the service
   *
   * @return list of {@link ProductDto} objects that are supported by the service
   */
  List<ProductDto> getAllProducts();
}

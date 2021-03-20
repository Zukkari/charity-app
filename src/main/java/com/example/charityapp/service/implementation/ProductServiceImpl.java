package com.example.charityapp.service.implementation;

import com.example.charityapp.dto.ProductDto;
import com.example.charityapp.repository.ProductLineItemRepository;
import com.example.charityapp.repository.ProductRepository;
import com.example.charityapp.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
  private final ProductLineItemRepository productLineItemRepository;
  private final ProductRepository productRepository;
  private final ModelMapper mapper;

  @Autowired
  public ProductServiceImpl(
      ProductLineItemRepository productLineItemRepository,
      ProductRepository productRepository,
      ModelMapper mapper) {
    this.productLineItemRepository = productLineItemRepository;
    this.productRepository = productRepository;
    this.mapper = mapper;
  }

  @Override
  public List<ProductDto> getAllProducts() {
    var dtoList =
        productRepository.findAll().stream()
            .map(product -> mapper.map(product, ProductDto.class))
            .collect(Collectors.toList());

    for (ProductDto dto : dtoList) {
      var count = productLineItemRepository.countByProductId(dto.getProductId());
      dto.setQuantity(count);
    }

    return dtoList;
  }
}

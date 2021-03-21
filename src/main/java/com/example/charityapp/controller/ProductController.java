package com.example.charityapp.controller;

import com.example.charityapp.dto.ProductDto;
import com.example.charityapp.service.ProductService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
@OpenAPIDefinition(
    tags = @Tag(name = "Product", description = "API responsible for product management"),
    info = @Info(version = "V1", title = "Product API"))
@CrossOrigin
public class ProductController {
  private static final Logger log = LoggerFactory.getLogger(ProductController.class);

  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @Operation(
      method = "GET",
      description = "Get all products provided by the service",
      operationId = "getAllProducts",
      tags = "Product")
  @GetMapping
  public List<ProductDto> getAllProducts() {
    log.info("Fetching all products...");
    var products = productService.getAllProducts();
    log.info("Finished fetching all products, fetched: {} products total", products.size());

    return products;
  }
}

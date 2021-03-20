package com.example.charityapp.controller;

import com.example.charityapp.dto.CartDto;
import com.example.charityapp.service.CartService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@OpenAPIDefinition(tags = @Tag(name = "Cart", description = "API responsible for cart management"))
public class CartController {
  private static final Logger log = LoggerFactory.getLogger(CartController.class);

  private final CartService cartService;

  @Autowired
  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @Operation(
      method = "POST",
      description = "Creates new instance of a cart and provides DTO representing new cart",
      summary = "Creates new instance of cart",
      tags = "Cart")
  @PostMapping
  public CartDto createBasket() {
    log.info("Creating new cart...");
    return cartService.createNewCart();
  }

  @Operation(
      method = "DELETE",
      description = "Delete the cart with provided id",
      summary = "Deletes the cart with provided cart id",
      tags = "Cart")
  @DeleteMapping("/{cartId}")
  public void deleteCart(@PathVariable("cartId") long cartId) {
    log.info("Deleting cart with id: {}", cartId);
    cartService.deleteCart(cartId);
  }
}

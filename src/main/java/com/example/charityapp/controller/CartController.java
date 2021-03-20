package com.example.charityapp.controller;

import com.example.charityapp.dto.CartDto;
import com.example.charityapp.dto.PaymentDto;
import com.example.charityapp.dto.ProductLineItemDto;
import com.example.charityapp.service.CartService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
      method = "GET",
      description = "Get entity with provided cart ID",
      summary = "Get entity that represents the cart with provided cart id",
      tags = "Cart")
  @GetMapping("/{cartId}")
  public CartDto getCart(@PathVariable("cartId") long cartId) {
    return cartService.getCart(cartId);
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

  @Operation(
      method = "POST",
      description = "Book line item for the cart with provided id",
      summary =
          "Book line item for the cart with provided id, if available line item for this product can be found",
      tags = "Cart",
      parameters = @Parameter(name = "cartId", description = "Id of the cart"))
  @PostMapping("/{cartId}/book")
  public CartDto bookItem(
      @PathVariable("cartId") long cartId,
      @Valid @RequestBody ProductLineItemDto productLineItemDto) {
    log.info(
        "Performing item booking for cart with id: {} and product with id : {}",
        cartId,
        productLineItemDto.getProductId());
    return cartService.bookItem(cartId, productLineItemDto);
  }

  @Operation(
      method = "POST",
      description = "Pay for the order that is contained inside the cart with provided id",
      summary =
          "Pay for the order with provided cart id. If payment is not sufficient for provided order, this operation will end with an exception",
      tags = "Cart",
      parameters = @Parameter(name = "cartId", description = "Id of the cart"))
  @PostMapping("/{cartId}/checkout")
  public CartDto checkout(@PathVariable("cartId") long cartId, @Valid @RequestBody PaymentDto dto) {
    log.info("Performing payment for cart id: {} and payment information: {}", cartId, dto);
    return cartService.checkout(cartId, dto);
  }
}

package com.example.charityapp.service;

import com.example.charityapp.dto.CartDto;

public interface CartService {

  /**
   * Create new cart instance
   *
   * @return {@link CartDto} that represents newly created cart
   */
  CartDto createNewCart();

  /**
   * Delete cart with provided cart id
   *
   * @param cartId id of the cart to delete
   */
  void deleteCart(long cartId);
}

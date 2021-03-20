package com.example.charityapp.service;

import com.example.charityapp.dto.CartDto;
import com.example.charityapp.dto.PaymentDto;
import com.example.charityapp.dto.ProductLineItemDto;

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

  /**
   * Book an item for provided cart id if the item is available. If no item is available for
   * required product, an exception will be thrown
   *
   * @param cartId id of the cart to book an item for
   * @param dto product line item that has product id to book a line item for
   * @return updated dto of the cart, if booking was possible
   * @throws com.example.charityapp.exceptions.CartNotFoundException - if cart was not found
   * @throws com.example.charityapp.exceptions.NoItemAvailableException - if no items are available
   *     for booking
   */
  CartDto bookItem(long cartId, ProductLineItemDto dto);

  /**
   * Checkout and pay for the order contained inside the cart with provided cart id
   *
   * @param cartId id of the cart to make the payment for
   * @param dto payment information for this order
   * @return updated dto of the cart, if order could be completed
   */
  CartDto checkout(long cartId, PaymentDto dto);
}

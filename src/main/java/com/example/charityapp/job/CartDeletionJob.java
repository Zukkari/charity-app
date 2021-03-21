package com.example.charityapp.job;

import com.example.charityapp.model.Cart;
import com.example.charityapp.repository.CartRepository;
import com.example.charityapp.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class CartDeletionJob implements Runnable {
  private static final Logger log = LoggerFactory.getLogger(CartDeletionJob.class);

  private final CartService cartService;
  private final CartRepository cartRepository;

  @Autowired
  public CartDeletionJob(CartService cartService, CartRepository cartRepository) {
    this.cartService = cartService;
    this.cartRepository = cartRepository;
  }

  @Override
  public void run() {
    log.info("Executing cart deletion job...");

    var dateToCheck = LocalDateTime.now().minus(1, ChronoUnit.HOURS);
    log.info("Looking for carts that were modified before: {}", dateToCheck);

    var cartsToDelete = cartRepository.findCartByModifiedBefore(dateToCheck);
    log.info("Found: {} carts to delete", cartsToDelete.size());

    for (Cart cart : cartsToDelete) {
      cartService.deleteCart(cart.getId());
    }

    log.info("Cart deletion job run finished");
  }
}

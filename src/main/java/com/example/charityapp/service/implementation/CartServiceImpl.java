package com.example.charityapp.service.implementation;

import com.example.charityapp.dto.CartDto;
import com.example.charityapp.model.Cart;
import com.example.charityapp.repository.CartRepository;
import com.example.charityapp.service.CartService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
  private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

  private final CartRepository cartRepository;
  private final ModelMapper mapper;

  @Autowired
  public CartServiceImpl(CartRepository cartRepository, ModelMapper mapper) {
    this.cartRepository = cartRepository;
    this.mapper = mapper;
  }

  @Override
  public CartDto createNewCart() {
    log.info("Creating new cart...");
    var cart = new Cart();
    var savedCart = cartRepository.save(cart);

    log.info("Created new cart with id: {}", savedCart.getId());
    return mapper.map(savedCart, CartDto.class);
  }

  @Override
  public void deleteCart(long cartId) {
    log.info("Deleting cart with id: {}", cartId);
    cartRepository.deleteById(cartId);
    log.info("Cart with id: {} has been deleted", cartId);
  }
}

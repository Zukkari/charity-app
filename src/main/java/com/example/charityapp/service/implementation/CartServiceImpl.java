package com.example.charityapp.service.implementation;

import com.example.charityapp.dto.CartDto;
import com.example.charityapp.dto.PaymentDto;
import com.example.charityapp.dto.ProductLineItemDto;
import com.example.charityapp.exceptions.CartNotFoundException;
import com.example.charityapp.exceptions.NoItemAvailableException;
import com.example.charityapp.model.Cart;
import com.example.charityapp.model.LineItemStatus;
import com.example.charityapp.model.ProductLineItem;
import com.example.charityapp.repository.CartRepository;
import com.example.charityapp.service.CartService;
import com.example.charityapp.service.ProductLineItemService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService {
  private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

  private final ProductLineItemService productLineItemService;

  private final CartRepository cartRepository;
  private final ModelMapper mapper;

  @Autowired
  public CartServiceImpl(
      ProductLineItemService productLineItemService,
      CartRepository cartRepository,
      ModelMapper mapper) {
    this.productLineItemService = productLineItemService;
    this.cartRepository = cartRepository;
    this.mapper = mapper;
  }

  @Override
  public CartDto createNewCart() {
    var cart = new Cart();
    var savedCart = cartRepository.save(cart);

    log.info("Created new cart with id: {}", savedCart.getId());
    return mapper.map(savedCart, CartDto.class);
  }

  @Override
  @Transactional
  public void deleteCart(long cartId) {
    log.info("Deleting cart with id: {}", cartId);
    var cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
    if (cart.getItems() == null) {
      cartRepository.deleteById(cartId);
      return;
    }

    for (ProductLineItem item : cart.getItems()) {
      productLineItemService.release(item);
    }

    cartRepository.deleteById(cartId);
    log.info("Cart with id: {} has been deleted", cartId);
  }

  @Override
  @Transactional
  public CartDto bookItem(long cartId, ProductLineItemDto dto) {
    var cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
    log.info("Cart with id: {} was found successfully", cartId);

    var productId = dto.getProductId();
    log.info("Looking for available line items for product with id: {}", productId);
    var lineItem =
        productLineItemService
            .getLineItem(productId, LineItemStatus.OPEN)
            .orElseThrow(() -> new NoItemAvailableException(productId));

    log.info("Found line item for cart: {} with id: {}", cartId, lineItem.getId());

    cart.addItem(lineItem);
    productLineItemService.book(lineItem);

    return mapper.map(cartRepository.save(cart), CartDto.class);
  }

  @Override
  public CartDto checkout(long cartId, PaymentDto dto) {
    throw new UnsupportedOperationException("Not implemented!");
  }
}

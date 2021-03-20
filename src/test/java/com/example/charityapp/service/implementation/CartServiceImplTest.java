package com.example.charityapp.service.implementation;

import com.example.charityapp.dto.CartDto;
import com.example.charityapp.model.Cart;
import com.example.charityapp.repository.CartRepository;
import com.example.charityapp.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

class CartServiceImplTest {

  private CartRepository cartRepository;
  private ModelMapper mapper;

  private CartService cartService;

  @BeforeEach
  void setUp() {
    this.cartRepository = mock(CartRepository.class);
    this.mapper = spy(new ModelMapper());

    this.cartService = new CartServiceImpl(cartRepository, mapper);
  }

  @Test
  void test_createCart() {
    var cart = new Cart();

    given(cartRepository.save(any())).willReturn(cart);

    var dto = cartService.createNewCart();

    then(cartRepository).should().save(any());
    then(mapper).should().map(eq(cart), eq(CartDto.class));

    assertThat(dto).isNotNull();
    assertThat(dto.getItems()).isNull();
    assertThat(dto.getPaidTime()).isNull();
    assertThat(dto.getPaidAmount()).isNull();
  }

  @Test
  void test_delete_cart() {
    cartService.deleteCart(1);
    then(cartRepository).should().deleteById(eq(1L));
  }
}

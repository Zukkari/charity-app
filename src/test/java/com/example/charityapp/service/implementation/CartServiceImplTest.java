package com.example.charityapp.service.implementation;

import com.example.charityapp.dto.CartDto;
import com.example.charityapp.dto.ProductLineItemDto;
import com.example.charityapp.exceptions.CartNotFoundException;
import com.example.charityapp.exceptions.NoItemAvailableException;
import com.example.charityapp.model.Cart;
import com.example.charityapp.model.LineItemStatus;
import com.example.charityapp.model.ProductLineItem;
import com.example.charityapp.repository.CartRepository;
import com.example.charityapp.service.CartService;
import com.example.charityapp.service.ProductLineItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

class CartServiceImplTest {

  private CartRepository cartRepository;
  private ModelMapper mapper;
  private ProductLineItemService productLineItemService;

  private CartService cartService;

  @BeforeEach
  void setUp() {
    this.cartRepository = mock(CartRepository.class);
    this.mapper = spy(new ModelMapper());
    this.productLineItemService = mock(ProductLineItemService.class);

    this.cartService = new CartServiceImpl(productLineItemService, cartRepository, mapper);
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
    var cart = new Cart();
    var item = new ProductLineItem();
    cart.setItems(List.of(item));

    given(cartRepository.findById(any())).willReturn(Optional.of(cart));

    cartService.deleteCart(1);

    then(cartRepository).should().deleteById(eq(1L));
    then(productLineItemService).should().release(eq(item));
  }

  @Test
  void test_book_item_no_cart() {
    var dto = new ProductLineItemDto();
    assertThrows(CartNotFoundException.class, () -> cartService.bookItem(10L, dto));
  }

  @Test
  void test_book_item_no_item() {
    var dto = new ProductLineItemDto();
    dto.setProductId(10L);

    given(cartRepository.findById(anyLong())).willReturn(Optional.of(new Cart()));

    given(productLineItemService.getLineItem(anyLong(), eq(LineItemStatus.OPEN)))
        .willReturn(Optional.empty());

    assertThrows(NoItemAvailableException.class, () -> cartService.bookItem(10L, dto));
  }

  @Test
  void test_booking_ok() {
    var now = LocalDateTime.now();

    var dto = new ProductLineItemDto();
    dto.setProductId(10L);

    var cart = spy(new Cart());
    cart.setId(1);
    given(cartRepository.findById(anyLong())).willReturn(Optional.of(cart));
    given(cartRepository.save(any())).willReturn(cart);

    var productLineItem = new ProductLineItem();
    given(productLineItemService.getLineItem(anyLong(), eq(LineItemStatus.OPEN)))
        .willReturn(Optional.of(productLineItem));

    var response = cartService.bookItem(1L, dto);

    then(cartRepository).should().findById(eq(1L));
    then(productLineItemService).should().getLineItem(eq(10L), eq(LineItemStatus.OPEN));
    then(cart).should().addItem(eq(productLineItem));
    then(productLineItemService).should().book(eq(productLineItem));

    assertThat(cart.getModified()).isAfter(now);

    assertThat(response.getId()).isEqualTo(1L);
    assertThat(response.getItems()).isNotEmpty().size().isEqualTo(1);
  }

  @Test
  void test_get_cart() {
    var cart = new Cart();
    given(cartRepository.findById(anyLong())).willReturn(Optional.of(cart));

    var dto = cartService.getCart(1L);

    then(cartRepository).should().findById(eq(1L));

    assertThat(dto).isNotNull();
  }
}

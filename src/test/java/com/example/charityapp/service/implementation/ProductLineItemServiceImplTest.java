package com.example.charityapp.service.implementation;

import com.example.charityapp.model.LineItemStatus;
import com.example.charityapp.model.ProductLineItem;
import com.example.charityapp.repository.ProductLineItemRepository;
import com.example.charityapp.service.ProductLineItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class ProductLineItemServiceImplTest {
  private ProductLineItemRepository productLineItemRepository;

  private ProductLineItemService productLineItemService;

  @BeforeEach
  void setUp() {
    this.productLineItemRepository = mock(ProductLineItemRepository.class);
    this.productLineItemService = new ProductLineItemServiceImpl(productLineItemRepository);
  }

  @Test
  void test_get_line_item() {
    productLineItemService.getLineItem(1L, LineItemStatus.OPEN);

    then(productLineItemRepository)
        .should()
        .findProductLineItemByProductIdAndLineItemStatus(eq(1L), eq(LineItemStatus.OPEN));
  }

  @Test
  void test_book() {
    var productLineItem = new ProductLineItem();

    productLineItemService.book(productLineItem);

    then(productLineItemRepository)
        .should()
        .save(argThat(arg -> arg.getLineItemStatus() == LineItemStatus.BOOKED));
  }

  @Test
  void test_release() {
    var productLineItem = new ProductLineItem();

    productLineItemService.release(productLineItem);

    then(productLineItemRepository)
        .should()
        .save(argThat(arg -> arg.getLineItemStatus() == LineItemStatus.OPEN));
  }

  @Test
  void test_purchase() {
    var productLineItem = new ProductLineItem();

    productLineItemService.purchase(productLineItem);

    then(productLineItemRepository)
        .should()
        .save(argThat(arg -> arg.getLineItemStatus() == LineItemStatus.SOLD));
  }
}

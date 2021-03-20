package com.example.charityapp.service;

import com.example.charityapp.model.LineItemStatus;
import com.example.charityapp.model.ProductLineItem;

import java.util.Optional;

public interface ProductLineItemService {

  /**
   * Get product line item for provided product id with item status
   *
   * @param productId id of the product to fetch the line item for
   * @param status status of the line item
   * @return instance of {@link ProductLineItem} if found, {@literal null} otherwise
   */
  Optional<ProductLineItem> getLineItem(long productId, LineItemStatus status);

  /**
   * Book the provided line item
   *
   * @param item item to book
   */
  void book(ProductLineItem item);

  /**
   * Release the booking from provided line item
   *
   * @param item item which booking to release
   */
  void release(ProductLineItem item);

  /**
   * Purchase this item
   *
   * @param item item to set as purchased
   */
  void purchase(ProductLineItem item);
}

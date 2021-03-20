package com.example.charityapp.repository;

import com.example.charityapp.model.LineItemStatus;
import com.example.charityapp.model.ProductLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductLineItemRepository extends JpaRepository<ProductLineItem, Long> {

  /**
   * Get product item count for provided product it
   *
   * @param productId id of the product to fetch the count for
   * @return count of product items for provided product id
   */
  long countByProductId(long productId);

  /**
   * Retrieve line item that is for the required product id and has provided status
   *
   * @param productId id of the product to fetch the line item for
   * @param status status of the item to fetch
   * @return maybe a product line item that matches the search criteria
   */
  Optional<ProductLineItem> findProductLineItemByProductIdAndLineItemStatus(
      long productId, LineItemStatus status);
}

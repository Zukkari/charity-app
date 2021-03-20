package com.example.charityapp.repository;

import com.example.charityapp.model.ProductLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLineItemRepository extends JpaRepository<ProductLineItem, Long> {

  /**
   * Get product item count for provided product it
   *
   * @param productId id of the product to fetch the count for
   * @return count of product items for provided product id
   */
  long countByProductId(long productId);
}

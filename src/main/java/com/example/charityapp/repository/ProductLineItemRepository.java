package com.example.charityapp.repository;

import com.example.charityapp.model.ProductLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
  @Query(
      value =
          "select count(p) from ProductLineItem p where p.product.id = :productId and p.lineItemStatus = 'OPEN'")
  long countByProductId(long productId);

  /**
   * Retrieve line item that is for the required product id and has provided status
   *
   * @param productId id of the product to fetch the line item for
   * @param status status of the item to fetch
   * @return maybe a product line item that matches the search criteria
   */
  @Query(
      value =
          "select * from product_line_item where product_id = :productId and line_item_status = :status limit 1",
      nativeQuery = true)
  Optional<ProductLineItem> findProductLineItemByProductIdAndLineItemStatus(
      long productId, String status);
}

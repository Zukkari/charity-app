package com.example.charityapp.service.implementation;

import com.example.charityapp.model.LineItemStatus;
import com.example.charityapp.model.ProductLineItem;
import com.example.charityapp.repository.ProductLineItemRepository;
import com.example.charityapp.service.ProductLineItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductLineItemServiceImpl implements ProductLineItemService {
  private static final Logger log = LoggerFactory.getLogger(ProductLineItemServiceImpl.class);

  private final ProductLineItemRepository productLineItemRepository;

  @Autowired
  public ProductLineItemServiceImpl(ProductLineItemRepository productLineItemRepository) {
    this.productLineItemRepository = productLineItemRepository;
  }

  @Override
  public Optional<ProductLineItem> getLineItem(long productId, LineItemStatus status) {
    log.info("Looking for line item for product: {} with status: {}", productId, status);
    return productLineItemRepository.findProductLineItemByProductIdAndLineItemStatus(
        productId, status.name());
  }

  @Override
  public void book(ProductLineItem item) {
    log.info("Booking item with id: {}", item.getId());

    item.setLineItemStatus(LineItemStatus.BOOKED);
    productLineItemRepository.save(item);

    log.info("Item with id: {} has been booked", item.getId());
  }

  @Override
  public void release(ProductLineItem item) {
    log.info("Releasing booking from item: {}", item.getId());

    item.setLineItemStatus(LineItemStatus.OPEN);
    productLineItemRepository.save(item);

    log.info("Item with id: {} was released from booking", item.getId());
  }

  @Override
  public void purchase(ProductLineItem item) {
    log.info("Marking item: {} as sold", item.getId());

    item.setLineItemStatus(LineItemStatus.SOLD);
    productLineItemRepository.save(item);

    log.info("Item with id: {} was sold", item.getId());
  }
}

package com.example.charityapp.repository;

import com.example.charityapp.model.ProductLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLineItemRepository extends JpaRepository<ProductLineItem, Long> {}

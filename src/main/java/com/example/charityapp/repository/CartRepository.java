package com.example.charityapp.repository;

import com.example.charityapp.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

  List<Cart> findCartByModifiedBefore(LocalDateTime before);
}

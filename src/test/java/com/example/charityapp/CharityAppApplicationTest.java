package com.example.charityapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CharityAppApplicationTest {

  @Test
  void context_loads() {
    assertTrue(true, "Context loads");
  }
}

package com.example.charityapp.controller;

import com.example.charityapp.dto.ProductDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

  @Autowired MockMvc mvc;

  @Autowired ObjectMapper mapper;

  @Test
  void test_getAllProducts() throws Exception {
    var response =
        mvc.perform(get("/product").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    var body = mapper.readValue(response.getContentAsByteArray(), ProductDto[].class);

    assertThat(body)
        .isNotEmpty()
        .hasSize(9)
        .anyMatch(
            dto ->
                dto.getProductId() == 1
                    && dto.getName().equals("Brownie")
                    && dto.getPrice().equals(BigDecimal.valueOf(0.65)));
  }
}

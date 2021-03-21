package com.example.charityapp.controller;

import com.example.charityapp.dto.CartDto;
import com.example.charityapp.dto.PaymentDto;
import com.example.charityapp.dto.ProductDto;
import com.example.charityapp.dto.ProductLineItemDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

  @Autowired MockMvc mockMvc;

  @Autowired ObjectMapper mapper;

  @Test
  void test_create_new_cart() throws Exception {
    mockMvc
        .perform(post("/cart"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", instanceOf(Integer.class)));
  }

  @Test
  void void_test_getCart() throws Exception {
    long id = createCart();

    mockMvc
        .perform(get("/cart/" + id))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", instanceOf(Integer.class)))
        .andExpect(jsonPath("$.items", empty()));
  }

  @Test
  void test_delete_cart() throws Exception {
    long id = createCart();

    mockMvc.perform(delete("/cart/" + id)).andExpect(status().isOk());

    mockMvc.perform(get("/cart/" + id)).andExpect(status().isNotFound());
  }

  @Test
  void test_book_item() throws Exception {
    long id = createCart();

    var lineItemDto = new ProductLineItemDto();
    lineItemDto.setProductId(1);

    var payload = mapper.writeValueAsBytes(lineItemDto);

    var responseBody = bookItem(id, payload).andReturn().getResponse().getContentAsByteArray();

    var responseCartDto = mapper.readValue(responseBody, CartDto.class);

    assertThat(responseCartDto.getItems()).isNotNull().isNotEmpty().size().isEqualTo(1);
  }

  @Test
  void test_checkout() throws Exception {
    var cartId = createCart();

    var lineItemDto = new ProductLineItemDto();
    lineItemDto.setProductId(1);

    var payload = mapper.writeValueAsBytes(lineItemDto);

    bookItem(cartId, payload);

    var paymentDto = new PaymentDto();
    paymentDto.setAmount(BigDecimal.TEN);

    var paymentPayload = mapper.writeValueAsBytes(paymentDto);
    mockMvc
        .perform(
            post("/cart/" + cartId + "/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(paymentPayload))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.total", is(0.65)))
        .andExpect(jsonPath("$.paidAmount", is(10)))
        .andExpect(
            jsonPath(
                "$.paidTime", matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d+")));
  }

  @Test
  void test_delete_releases_items() throws Exception {
    var cartId = createCart();

    var lineItemDto = new ProductLineItemDto();
    lineItemDto.setProductId(1);

    var productState = getProducts();

    var productCount =
        productState.stream()
            .filter(dto -> dto.getProductId() == 1)
            .findAny()
            .map(ProductDto::getQuantity)
            .orElse(0L);

    var payload = mapper.writeValueAsBytes(lineItemDto);

    bookItem(cartId, payload);

    var postBookingProductState = getProducts();
    var postBookingProductCount =
        postBookingProductState.stream()
            .filter(dto -> dto.getProductId() == 1)
            .findAny()
            .map(ProductDto::getQuantity)
            .orElse(0L);

    assertThat(postBookingProductCount).isLessThan(productCount);

    // Delete cart

    mockMvc.perform(delete("/cart/" + cartId))
            .andExpect(status().isOk());

    var postDeleteProductState = getProducts();
    var postDeleteProductCount =
            postDeleteProductState.stream()
                    .filter(dto -> dto.getProductId() == 1)
                    .findAny()
                    .map(ProductDto::getQuantity)
                    .orElse(0L);

    assertThat(postDeleteProductCount).isEqualTo(productCount);
  }

  private List<ProductDto> getProducts() throws Exception {
    var body =
        mockMvc
            .perform(get("/product"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsByteArray();

    return Arrays.asList(mapper.readValue(body, ProductDto[].class));
  }

  private ResultActions bookItem(long id, byte[] payload) throws Exception {
    return mockMvc
        .perform(
            post("/cart/" + id + "/book").content(payload).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.items[0].productName", is("Brownie")))
        .andExpect(jsonPath("$.items[0].itemStatus", is("BOOKED")));
  }

  private long createCart() throws Exception {
    var body =
        mockMvc
            .perform(post("/cart"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse()
            .getContentAsByteArray();

    var dto = mapper.readValue(body, CartDto.class);
    return dto.getId();
  }
}

package com.example.charityapp.controller;

import com.example.charityapp.dto.ErrorDto;
import com.example.charityapp.exceptions.CartNotFoundException;
import com.example.charityapp.exceptions.NoItemAvailableException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CharityAppExceptionHandler {
  private static final Logger log = LoggerFactory.getLogger(CharityAppExceptionHandler.class);

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(CartNotFoundException.class)
  @ApiResponse(responseCode = "404", description = "The cart with provided id was not found")
  public ErrorDto handleCartNotFound(CartNotFoundException e) {
    log.debug("Cart with id: {} was not found", e.getProvidedId());
    return new ErrorDto("Cart with provided id: " + e.getProvidedId() + " was not found");
  }

  @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
  @ExceptionHandler(NoItemAvailableException.class)
  @ApiResponse(responseCode = "417", description = "No available item was found for provided product")
  public ErrorDto handleNoLineItem(NoItemAvailableException e) {
    log.debug("No available item was found for provided product: {}", e.getProductId());
    return new ErrorDto("No available item was found for provided product: " + e.getProductId());
  }
}

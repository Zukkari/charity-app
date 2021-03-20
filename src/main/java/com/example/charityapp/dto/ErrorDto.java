package com.example.charityapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Error", description = "Generic entity received as an response from the API")
public class ErrorDto {
  private final String message;

  public ErrorDto(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}

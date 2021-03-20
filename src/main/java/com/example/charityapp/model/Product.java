package com.example.charityapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Data
public class Product {

  @Id private long id;

  private String name;

  private BigDecimal price;
}

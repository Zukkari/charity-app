package com.example.charityapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Basket {

  @Id private long id;

  private LocalDateTime created;

  private LocalDateTime modified;

  @OneToMany private List<BasketLineItem> items;

  private BigDecimal paidAmount;
}

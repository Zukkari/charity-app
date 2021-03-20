package com.example.charityapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Data
public class BasketLineItem {

  @Id private long id;

  @ManyToOne private Product product;
}

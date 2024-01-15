package com.strandhvilliam.analyticsservice.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class SoldProductEntity {

  @Id
  @Column(name = "product_id")
  private String productId;

  @Column(name = "restaurant_id")
  private String restaurantId;

  @Column(name = "product_name")
  private String productName;

  @Column(name = "product_price")
  private double productPrice;

  @Column(name = "quantity")
  private int quantity;

}

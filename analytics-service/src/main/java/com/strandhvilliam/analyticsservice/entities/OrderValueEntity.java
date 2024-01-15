package com.strandhvilliam.analyticsservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderValueEntity {

  @Id()
  @Column(name = "order_id")
  private String orderId;
  @Column(name = "restaurant_id")
  private String restaurantId;
  @Column(name = "order_value")
  private double orderValue;

}

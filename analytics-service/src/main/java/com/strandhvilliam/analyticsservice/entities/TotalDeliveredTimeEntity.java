package com.strandhvilliam.analyticsservice.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalDeliveredTimeEntity {

  @Id
  @Column(name = "order_id")
  private String orderId;

  @Column(name = "driver_id")
  private String driverId;

  @Column(name = "restaurant_id")
  private String restaurantId;

  @Column(name = "total_delivery_seconds")
  private long totalDeliverySeconds;

}

package com.strandhvilliam.analyticsservice.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinishedToPickupTimeEntity {

  @Id
  @Column(name = "order_id")
  private String orderId;

  @Column(name = "driver_id")
  private String driverId;

  @Column(name = "restaurant_id")
  private String restaurantId;

  @Column(name = "finished_to_pickup_seconds")
  private long finishedToPickupSeconds;

}

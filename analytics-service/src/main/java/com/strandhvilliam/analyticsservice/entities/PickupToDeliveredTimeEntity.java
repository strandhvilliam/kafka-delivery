package com.strandhvilliam.analyticsservice.entities;


import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.C;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PickupToDeliveredTimeEntity {

  @Id
  @Column(name = "order_id")
  private String orderId;

  @Column(name = "driver_id")
  private String driverId;

  @Column(name = "pickup_to_delivered_seconds")
  private long pickupToDeliveredSeconds;

}

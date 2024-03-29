package com.strandhvilliam.driveravailability.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.strandhvilliam.driveravailability.utils.Coordinates;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jobs")
public class JobEntity {

  @Id()
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  @Column(name = "order_id")
  private String orderId;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "latitude", column = @Column(name = "destination_latitude")),
      @AttributeOverride(name = "longitude", column = @Column(name = "destination_longitude"))
  })
  private Coordinates destination;
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "latitude", column = @Column(name = "origin_latitude")),
      @AttributeOverride(name = "longitude", column = @Column(name = "origin_longitude"))
  })
  private Coordinates origin;

  @Column(name = "completed")
  @Builder.Default
  private boolean completed = false;

  @Column(name = "restaurant_id")
  private String restaurantId;

  @Column(name = "customer_id")
  private String customerId;

  @ManyToOne
  @JoinColumn(name = "driver_id")
  @JsonIgnore
  private DriverEntity driver;
}

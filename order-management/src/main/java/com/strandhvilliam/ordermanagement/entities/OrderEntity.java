package com.strandhvilliam.ordermanagement.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class OrderEntity {

  @Id()
  private String id;
  @Column(name = "created_at")
  private String createdAt;
  @Column(name = "status")
  private String status;
  @Column(name = "customer_id")
  private String customerId;
  @Column(name = "restaurant_id")
  private String restaurantId;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<OrderItemEntity> items;

}

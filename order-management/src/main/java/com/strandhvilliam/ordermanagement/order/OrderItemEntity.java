package com.strandhvilliam.ordermanagement.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItemEntity {

  @Id()
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  @Column(name = "product_id")
  private String productId;
  @Column(name = "description")
  private String description;
  @Column(name = "cost")
  private double cost;

  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  private OrderEntity order;

}

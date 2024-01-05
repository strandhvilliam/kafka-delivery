package com.strandhvilliam.ordermanagement.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

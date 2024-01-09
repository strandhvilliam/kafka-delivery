package com.strandhvilliam.ordermanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
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
  @JoinColumn(name = "order_id")
  @JsonIgnore
  private OrderEntity order;

}

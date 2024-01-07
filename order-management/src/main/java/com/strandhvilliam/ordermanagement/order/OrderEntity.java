package com.strandhvilliam.ordermanagement.order;

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
  @Column(name = "date")
  private String date;
  @Column(name = "status")
  private String status;
  @Column(name = "user_id")
  private String userId;
  @Column(name = "restaurant_id")
  private String restaurantId;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderItemEntity> items;

}

package com.strandhvilliam.ordermanagement.order;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

  @OneToMany(mappedBy = "order")
  private List<OrderItemEntity> items;

}

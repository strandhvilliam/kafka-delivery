package com.strandhvilliam.ordermanagement.repositories;

import com.strandhvilliam.ordermanagement.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {

  List<OrderEntity> findByRestaurantId(String restaurantId);

  List<OrderEntity> findByCustomerId(String customerId);
}

package com.strandhvilliam.analyticsservice.repositories;

import com.strandhvilliam.analyticsservice.entities.OrderValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderValueRepository extends JpaRepository<OrderValueEntity, String> {

  List<OrderValueEntity> findAllByRestaurantId(String restaurantId);

}

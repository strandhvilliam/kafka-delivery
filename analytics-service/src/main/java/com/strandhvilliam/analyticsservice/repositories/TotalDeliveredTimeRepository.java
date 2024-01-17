package com.strandhvilliam.analyticsservice.repositories;

import com.strandhvilliam.analyticsservice.entities.TotalDeliveredTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TotalDeliveredTimeRepository extends JpaRepository<TotalDeliveredTimeEntity, String> {

  List<TotalDeliveredTimeEntity> findAllByRestaurantId(String restaurantId);
}

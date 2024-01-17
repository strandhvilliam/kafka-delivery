package com.strandhvilliam.analyticsservice.repositories;

import com.strandhvilliam.analyticsservice.entities.RestaurantFinishTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantFinishTimeRepository extends JpaRepository<RestaurantFinishTimeEntity, String> {

  List<RestaurantFinishTimeEntity> findAllByRestaurantId(String restaurantId);
}

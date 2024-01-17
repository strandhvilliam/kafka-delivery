package com.strandhvilliam.analyticsservice.repositories;

import com.strandhvilliam.analyticsservice.entities.SoldProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoldProductRepository extends JpaRepository<SoldProductEntity, String> {

  List<SoldProductEntity> findAllByRestaurantId(String restaurantId);
}

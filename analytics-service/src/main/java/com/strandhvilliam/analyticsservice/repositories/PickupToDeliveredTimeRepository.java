package com.strandhvilliam.analyticsservice.repositories;

import com.strandhvilliam.analyticsservice.entities.PickupToDeliveredTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PickupToDeliveredTimeRepository extends JpaRepository<PickupToDeliveredTimeEntity, String> {
  List<PickupToDeliveredTimeEntity> findAllByDriverId(String driverId);
}

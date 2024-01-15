package com.strandhvilliam.analyticsservice.repositories;

import com.strandhvilliam.analyticsservice.entities.FinishedToPickupTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinishedToPickupTimeRepository extends JpaRepository<FinishedToPickupTimeEntity, String> {

}

package com.strandhvilliam.driveravailability.repositories;

import com.strandhvilliam.driveravailability.entities.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverEntityRepository extends JpaRepository<DriverEntity, String> {
}

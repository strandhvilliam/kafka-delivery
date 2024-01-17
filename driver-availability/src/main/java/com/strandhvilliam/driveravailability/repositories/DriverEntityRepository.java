package com.strandhvilliam.driveravailability.repositories;

import com.strandhvilliam.driveravailability.entities.DriverEntity;
import com.strandhvilliam.driveravailability.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverEntityRepository extends JpaRepository<DriverEntity, String> {
}

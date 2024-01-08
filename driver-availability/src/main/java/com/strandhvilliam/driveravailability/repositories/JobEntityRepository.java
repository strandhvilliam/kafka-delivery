package com.strandhvilliam.driveravailability.repositories;

import com.strandhvilliam.driveravailability.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobEntityRepository extends JpaRepository<JobEntity, String> {
}

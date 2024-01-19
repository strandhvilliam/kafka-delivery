package com.strandhvilliam.driveravailability.services;

import com.strandhvilliam.driveravailability.entities.DriverEntity;
import com.strandhvilliam.driveravailability.entities.JobEntity;
import com.strandhvilliam.driveravailability.producers.DriverAssignedProducer;
import com.strandhvilliam.driveravailability.repositories.DriverEntityRepository;
import com.strandhvilliam.driveravailability.repositories.JobEntityRepository;
import com.strandhvilliam.driveravailability.utils.Coordinates;
import com.strandhvilliam.driveravailability.utils.CustomLogger;
import com.strandhvilliam.orderevent.proto.OrderEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;

@Service
public class AvailabilityService {

  private final DriverEntityRepository driverRepository;

  private final JobEntityRepository jobRepository;

  private final CustomLogger log;

  private final DriverAssignedProducer driverAssignedProducer;

  public AvailabilityService(
      JobEntityRepository jobRepository,
      DriverEntityRepository driverRepository, CustomLogger log,
      DriverAssignedProducer driverAssignedProducer) {
    this.jobRepository = jobRepository;
    this.driverRepository = driverRepository;
    this.log = log;
    this.driverAssignedProducer = driverAssignedProducer;
  }

  public void assignDriver(OrderEvent orderEvent) {
    log.info(String.format("Assigning driver for order: %s", orderEvent.getId()), AvailabilityService.class.getSimpleName());
    var job = JobEntity.builder()
        .destination(getCustomerCoordinates(orderEvent.getCustomerId()))
        .origin(getRestaurantCoordinates(orderEvent.getRestaurantId()))
        .driver(findSuitableDriver())
        .customerId(orderEvent.getCustomerId())
        .createdAt(LocalDateTime.now())
        .orderId(orderEvent.getId())
        .build();

    jobRepository.save(job);
    driverAssignedProducer.send(job);
  }

  private Coordinates getRestaurantCoordinates(String restaurantId) {
    // TODO: real implementation
    return new Coordinates(59.34219299290245, 18.040673510423836);
  }

  private Coordinates getCustomerCoordinates(String userId) {
    // TODO: real implementation
    return new Coordinates(59.322585760273626, 18.072111709197497);
  }

 /**
   * Find the driver with the least amount of jobs
   *
   * @return DriverEntity
   */
  private DriverEntity findSuitableDriver() {
    return driverRepository.findAll().stream()
        .sorted(Comparator.comparingInt(driver -> driver.getJobs().size()))
        .toList()
        .get(0);

  }
}

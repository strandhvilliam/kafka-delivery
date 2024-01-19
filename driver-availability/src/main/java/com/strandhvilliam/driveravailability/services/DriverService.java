package com.strandhvilliam.driveravailability.services;

import com.strandhvilliam.driveravailability.entities.DriverEntity;
import com.strandhvilliam.driveravailability.entities.JobEntity;
import com.strandhvilliam.driveravailability.grpc.*;
import com.strandhvilliam.driveravailability.repositories.DriverEntityRepository;
import com.strandhvilliam.driveravailability.repositories.JobEntityRepository;
import com.strandhvilliam.driveravailability.utils.CustomLogger;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Optional;

@GrpcService
public class DriverService extends DriverServiceGrpc.DriverServiceImplBase {

  private final CustomLogger log;

  private final DriverEntityRepository driverRepository;
  private final JobEntityRepository jobRepository;

  public DriverService(CustomLogger log, DriverEntityRepository driverRepository, JobEntityRepository jobRepository) {
    this.log = log;
    this.driverRepository = driverRepository;
    this.jobRepository = jobRepository;
  }

  @Override
  public void getJobByOrderId(OrderIdRequest req, StreamObserver<JobResponse> responseObserver) {
    log.info("Received new job request for order" + req.getOrderId(), DriverService.class.getSimpleName());
    Optional<JobEntity> optJob = jobRepository.findJobByOrderId(req.getOrderId());

    if (optJob.isPresent()) {
      var job = optJob.get();
      var response = JobResponse.newBuilder()
          .setId(job.getId())
          .setDestination(Coordinates.newBuilder()
              .setLatitude(job.getDestination().getLatitude())
              .setLongitude(job.getDestination().getLongitude())
              .build())
          .setOrigin(Coordinates.newBuilder()
              .setLatitude(job.getOrigin().getLatitude())
              .setLongitude(job.getOrigin().getLongitude())
              .build())
          .setDriverId(job.getDriver().getId())
          .setCreatedAt(job.getCreatedAt().toString())
          .setCustomerId(job.getCustomerId())
          .setCompleted(job.isCompleted())
          .setOrderId(job.getOrderId())
          .build();

      responseObserver.onNext(response);
      responseObserver.onCompleted();
    } else {
      throw new RuntimeException("Job not found");
    }
  }

  @Override
  public void getDriverById(DriverIdRequest req, StreamObserver<DriverResponse> responseObserver) {
log.info("Received new driver request for id" + req.getId(), DriverService.class.getSimpleName());
    Optional<DriverEntity> optDriver = driverRepository.findById(req.getId());

    if (optDriver.isPresent()) {
      var driver = optDriver.get();
      var response = DriverResponse.newBuilder()
          .setId(driver.getId())
          .setPhone(driver.getPhone())
          .setName(driver.getName())
          .setEmail(driver.getEmail())
          .addAllJobs(driver.getJobs().stream().map(this::buildJobResponse).toList())
          .build();

      responseObserver.onNext(response);
      responseObserver.onCompleted();
    } else {
      responseObserver.onError(new Exception("Driver not found"));
      responseObserver.onCompleted();
    }
  }

  private JobResponse buildJobResponse(JobEntity entity) {
    return JobResponse.newBuilder()
        .setId(entity.getId())
        .setDestination(Coordinates.newBuilder()
            .setLatitude(entity.getDestination().getLatitude())
            .setLongitude(entity.getDestination().getLongitude())
            .build())
        .setOrigin(Coordinates.newBuilder()
            .setLatitude(entity.getOrigin().getLatitude())
            .setLongitude(entity.getOrigin().getLongitude())
            .build())
        .setDriverId(entity.getDriver().getId())
        .setCreatedAt(entity.getCreatedAt().toString())
        .setCompleted(entity.isCompleted())
        .setOrderId(entity.getOrderId())
        .build();
  }

}

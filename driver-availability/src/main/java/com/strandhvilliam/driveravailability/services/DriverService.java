package com.strandhvilliam.driveravailability.services;

import com.strandhvilliam.driveravailability.entities.DriverEntity;
import com.strandhvilliam.driveravailability.entities.JobEntity;
import com.strandhvilliam.driveravailability.grpc.*;
import com.strandhvilliam.driveravailability.repositories.DriverEntityRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Optional;

@GrpcService
public class DriverService extends DriverServiceGrpc.DriverServiceImplBase {

  private final DriverEntityRepository driverRepository;

  public DriverService(DriverEntityRepository driverRepository) {
    this.driverRepository = driverRepository;
  }

  @Override
  public void getDriverById(DriverIdRequest req, StreamObserver<DriverResponse> responseObserver) {

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

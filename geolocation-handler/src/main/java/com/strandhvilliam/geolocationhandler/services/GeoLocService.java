package com.strandhvilliam.geolocationhandler.services;


import com.strandhvilliam.driveravailability.grpc.DriverServiceGrpc;
import com.strandhvilliam.driveravailability.grpc.OrderIdRequest;
import com.strandhvilliam.geolocevent.proto.Coordinates;
import com.strandhvilliam.geolocevent.proto.GeoLocEvent;
import com.strandhvilliam.geolocationhandler.grpc.GeoLocRequest;
import com.strandhvilliam.geolocationhandler.grpc.GeoLocResponse;
import com.strandhvilliam.geolocationhandler.grpc.GeoLocServiceGrpc;
import com.strandhvilliam.geolocationhandler.producers.GeoLocProducer;
import com.strandhvilliam.geolocevent.proto.Coordinates;
import com.strandhvilliam.geolocevent.proto.GeoLocEvent;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

@GrpcService
public class GeoLocService extends GeoLocServiceGrpc.GeoLocServiceImplBase {

  private static final String DRIVER_AVAILABILITY_CLIENT = "driver-availability";

  private final Logger log = LoggerFactory.getLogger(GeoLocService.class.getSimpleName());


  @GrpcClient(DRIVER_AVAILABILITY_CLIENT)
  private DriverServiceGrpc.DriverServiceBlockingStub driverServiceBlockingStub;

  private final GeoLocProducer geoLocProducer;

  public GeoLocService(GeoLocProducer geoLocProducer) {
    this.geoLocProducer = geoLocProducer;
  }

  /**
   * Receives the geo location of a driver and sends it to the kafka topic
   * @param request          geo location request
   * @param responseObserver grpc response observer
   */
  @Override
  public void sendGeoLoc(GeoLocRequest request, StreamObserver<GeoLocResponse> responseObserver) {
    log.info("Received new geo location for order {}", request.getOrderId());

    var orderIdRequest = OrderIdRequest.newBuilder()
        .setOrderId(request.getOrderId())
        .build();

    // TODO: cache the driverdata and orderdata in redis to avoid fetching from db everytime
    var jobData = driverServiceBlockingStub.getJobByOrderId(orderIdRequest);

    var response = GeoLocResponse.newBuilder()
        .setId(UUID.randomUUID().toString())
        .setOrderId(request.getOrderId())
        .setDistance(calculateDistance(
            Coordinates.newBuilder()
                .setLatitude(request.getCoordinates().getLatitude())
                .setLongitude(request.getCoordinates().getLongitude())
                .build(),
            Coordinates.newBuilder()
                .setLatitude(jobData.getDestination().getLatitude())
                .setLongitude(jobData.getDestination().getLongitude())
                .build()))
        .setDriverId(request.getDriverId())
        .setCoordinates(request.getCoordinates())
        .setCustomerId(jobData.getCustomerId())
        .setTimestamp(LocalDateTime.now().toString())
        .build();

    var event = GeoLocEvent.newBuilder()
        .setId(response.getId())
        .setOrderId(response.getOrderId())
        .setDriverId(response.getDriverId())
        .setCustomerId(response.getCustomerId())
        .setDistance(response.getDistance())
        .setCoordinates(Coordinates.newBuilder()
            .setLatitude(response.getCoordinates().getLatitude())
            .setLongitude(response.getCoordinates().getLongitude())
            .build())
        .setTimestamp(response.getTimestamp())
        .build();

    geoLocProducer.send("delivery_geolocation_dev", event);

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

  /**
   * Calculates the distance between two coordinates in KM
   * Not exact but faster than the Haversine formula
   *
   * @param driverCoordinates   current driver coordinates
   * @param customerCoordinates customer coordinates
   * @return
   */
  private double calculateDistance(Coordinates driverCoordinates, Coordinates customerCoordinates) {
    final int EARTH_RADIUS = 6371; // Approx Earth radius in KM
    double lat1Rad = Math.toRadians(driverCoordinates.getLatitude());
    double lat2Rad = Math.toRadians(customerCoordinates.getLatitude());
    double lon1Rad = Math.toRadians(driverCoordinates.getLongitude());
    double lon2Rad = Math.toRadians(customerCoordinates.getLongitude());

    double x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2);
    double y = (lat2Rad - lat1Rad);

    return Math.sqrt(x * x + y * y) * EARTH_RADIUS;
  }

}

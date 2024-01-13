package com.strandhvilliam.driverapi.services;

import com.strandhvilliam.driverapi.dto.DriverLocationDto;
import com.strandhvilliam.driveravailability.grpc.DriverIdRequest;
import com.strandhvilliam.driveravailability.grpc.DriverServiceGrpc;
import com.strandhvilliam.geolocationhandler.grpc.GeoLocRequest;
import com.strandhvilliam.geolocationhandler.grpc.Coordinates;
import com.strandhvilliam.geolocationhandler.grpc.GeoLocServiceGrpc;
import com.strandhvilliam.ordermanagement.grpc.OrderManagementServiceGrpc;
import com.strandhvilliam.ordermanagement.grpc.UpdateOrderStatusRequest;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DriverApiService {

  private static final String DRIVER_AVAILABILITY_CLIENT = "driver-availability";
  private static final String ORDER_MANAGEMENT_CLIENT = "order-management";
  private static final String GEOLOCATION_HANDLER_CLIENT = "geolocation-handler";

  @GrpcClient(DRIVER_AVAILABILITY_CLIENT)
  private DriverServiceGrpc.DriverServiceBlockingStub driverService;

  @GrpcClient(ORDER_MANAGEMENT_CLIENT)
  private OrderManagementServiceGrpc.OrderManagementServiceBlockingStub orderManagementService;

  @GrpcClient(GEOLOCATION_HANDLER_CLIENT)
  private GeoLocServiceGrpc.GeoLocServiceBlockingStub geoLocService;

  private final Logger logger = LoggerFactory.getLogger(DriverApiService.class.getSimpleName());

  private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

  public void addEmitter(String driverId, SseEmitter emitter) {
    emitters.put(driverId, emitter);
    emitter.onCompletion(() -> emitters.remove(driverId));
    emitter.onTimeout(() -> emitters.remove(driverId));
    logger.info("Added emitter for driver: " + driverId);
  }


  public List<String> getJobs(String driverId) {
    var response = driverService.getDriverById(
        DriverIdRequest.newBuilder()
            .setId(driverId)
            .build());
    return response.getJobsList().stream().map(job -> "New job assigned: " + job.getOrderId()).toList();
  }

  public void sendEvent(String driverId, String orderId, String type) {
    var emitter = emitters.get(driverId);
    logger.info("Trying to send event to driver: " + driverId);
    logger.info("Trying to send event to emitter: " + emitter);
    if (emitter != null) {
      try {
        emitter.send(SseEmitter.event()
            .name(type)
            .data(orderId));
      } catch (Exception e) {
        emitters.remove(driverId);
      }
    }
  }

  public void pickupOrder(String orderId) {
    logger.info("Trying to pickup order: " + orderId);
    var request = UpdateOrderStatusRequest.newBuilder()
        .setOrderId(orderId)
        .setStatus("ORDER_PICKED_UP")
        .build();
    var response = orderManagementService.updateOrderStatus(request);
    logger.info("Picked up order: " + response.getId());
  }

  public void deliverOrder(String orderId) {
    logger.info("Trying to deliver order: " + orderId);
    var request = UpdateOrderStatusRequest.newBuilder()
        .setOrderId(orderId)
        .setStatus("ORDER_DELIVERED")
        .build();
    var response = orderManagementService.updateOrderStatus(request);
    logger.info("Delivered order: " + response.getId());
  }

  public void sendGeoLocation(DriverLocationDto dto) {
    logger.info("Sending geo location for order: " + dto.getOrderId());
    var request = GeoLocRequest.newBuilder()
        .setOrderId(dto.getOrderId())
        .setDriverId(dto.getDriverId())
        .setCoordinates(Coordinates.newBuilder()
            .setLatitude(dto.getLatitude())
            .setLongitude(dto.getLongitude())
            .build())
        .build();
    var response = geoLocService.sendGeoLoc(request);
    logger.info("Sent geo location for order: " + response.getOrderId());
  }
}

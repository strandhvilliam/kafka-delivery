package com.strandhvilliam.driverapi.services;

import com.strandhvilliam.driverapi.dto.DriverLocationDto;
import com.strandhvilliam.driverapi.utils.CustomLogger;
import com.strandhvilliam.driveravailability.grpc.DriverIdRequest;
import com.strandhvilliam.driveravailability.grpc.DriverServiceGrpc;
import com.strandhvilliam.driveravailability.grpc.JobResponse;
import com.strandhvilliam.driveravailability.grpc.OrderIdRequest;
import com.strandhvilliam.geolocationhandler.grpc.GeoLocRequest;
import com.strandhvilliam.geolocationhandler.grpc.Coordinates;
import com.strandhvilliam.geolocationhandler.grpc.GeoLocServiceGrpc;
import com.strandhvilliam.jobevent.proto.JobEvent;
import com.strandhvilliam.orderevent.proto.OrderEvent;
import com.strandhvilliam.ordermanagement.grpc.GetManyOrdersRequest;
import com.strandhvilliam.ordermanagement.grpc.GetOrderIdRequest;
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

  private final CustomLogger logger;

  private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

  public DriverApiService(CustomLogger logger) {
    this.logger = logger;
  }

  public void addEmitter(String driverId, SseEmitter emitter) {
    emitters.put(driverId, emitter);
    emitter.onCompletion(() -> emitters.remove(driverId));
    emitter.onTimeout(() -> emitters.remove(driverId));
    logger.info("Added emitter for driver: " + driverId, DriverApiService.class.getSimpleName());
  }


  public List<String> getJobs(String driverId) {
    logger.info("Trying to get jobs for driver: " + driverId, DriverApiService.class.getSimpleName());
    var response = driverService.getDriverById(
        DriverIdRequest.newBuilder()
            .setId(driverId)
            .build());

    var req = GetManyOrdersRequest.newBuilder()
        .addAllOrderIds(response.getJobsList()
            .stream()
            .map(JobResponse::getOrderId)
            .toList())
        .build();

    var orders = orderManagementService.getManyOrders(req);

    return orders.getOrdersList()
        .stream()
        .map(o -> "{ \"id\":\"" + o.getId() + "\",\"status\":\"" + o.getStatus() + "\"}")
        .toList();
  }

  public void emitDriverEvent(JobEvent event) {

    var order = orderManagementService.getOrder(
        GetOrderIdRequest.newBuilder()
            .setOrderId(event.getOrderId())
            .build());

    var emitter = emitters.get(event.getDriverId());
    logger.info("Trying to send event to driver: " + event.getDriverId(), DriverApiService.class.getSimpleName());
    logger.info("Trying to send event to emitter: " + emitter, DriverApiService.class.getSimpleName());
    if (emitter != null) {
      try {
        String json = "{ \"id\":\"" + event.getOrderId() + "\",\"status\":\"" + order.getStatus() + "\"}";
        emitter.send(json);
      } catch (Exception e) {
        emitters.remove(event.getDriverId());
      }
    }
  }

  public void emitOrderEvent(OrderEvent event) {

    var job = driverService.getJobByOrderId(
        OrderIdRequest.newBuilder()
            .setOrderId(event.getId())
            .build());

    var emitter = emitters.get(job.getDriverId());

    logger.info("Trying to send event to driver: " + job.getDriverId(), DriverApiService.class.getSimpleName());
    logger.info("Trying to send event to emitter: " + emitter, DriverApiService.class.getSimpleName());

    if (emitter != null) {
      try {
        String json = "{ \"id\":\"" + event.getId() + "\",\"status\":\"" + event.getStatus() + "\"}";
        emitter.send(json);
      } catch (Exception e) {
        emitters.remove(job.getDriverId());
      }
    }
  }

  public String pickupOrder(String orderId) {
    logger.info("Trying to pickup order: " + orderId, DriverApiService.class.getSimpleName());
    var request = UpdateOrderStatusRequest.newBuilder()
        .setOrderId(orderId)
        .setStatus("ORDER_PICKED_UP")
        .build();
    var response = orderManagementService.updateOrderStatus(request);
    logger.info("Picked up order: " + response.getId(), DriverApiService.class.getSimpleName());
    return "{ \"id\":\"" + response.getId() + "\",\"status\":\"" + response.getStatus() + "\"}";
  }

  public String deliverOrder(String orderId) {
    logger.info("Trying to deliver order: " + orderId, DriverApiService.class.getSimpleName());
    var request = UpdateOrderStatusRequest.newBuilder()
        .setOrderId(orderId)
        .setStatus("ORDER_DELIVERED")
        .build();
    var response = orderManagementService.updateOrderStatus(request);
    logger.info("Delivered order: " + response.getId(), DriverApiService.class.getSimpleName());
    return "{ \"id\":\"" + response.getId() + "\",\"status\":\"" + response.getStatus() + "\"}";
  }

  public void sendGeoLocation(DriverLocationDto dto) {
    logger.info("Sending geo location for order: " + dto.getOrderId(), DriverApiService.class.getSimpleName());
    var request = GeoLocRequest.newBuilder()
        .setOrderId(dto.getOrderId())
        .setDriverId(dto.getDriverId())
        .setCoordinates(Coordinates.newBuilder()
            .setLatitude(dto.getLatitude())
            .setLongitude(dto.getLongitude())
            .build())
        .build();
    var response = geoLocService.sendGeoLoc(request);
    logger.info("Sent geo location for order: " + response.getOrderId(), DriverApiService.class.getSimpleName());
  }
}

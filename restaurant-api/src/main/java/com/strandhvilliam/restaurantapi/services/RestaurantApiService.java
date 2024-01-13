package com.strandhvilliam.restaurantapi.services;

import com.strandhvilliam.events.proto.OrderEvent;
import com.strandhvilliam.ordermanagement.grpc.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RestaurantApiService {

  private static final String ORDER_MANAGEMENT_CLIENT = "order-management";
  private final Logger logger = LoggerFactory.getLogger(RestaurantApiService.class.getSimpleName());

  private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

  @GrpcClient(ORDER_MANAGEMENT_CLIENT)
  private OrderManagementServiceGrpc.OrderManagementServiceBlockingStub orderManagementService;

  public String getRestaurantOrders(String restaurantId) {
    logger.info("Trying to get orders for restaurant: " + restaurantId);
    var request = GetRestaurantOrdersRequest.newBuilder()
        .setRestaurantId(restaurantId)
        .build();
    var response = orderManagementService.getRestaurantOrders(request);
    logger.info("Got orders for restaurant: " + restaurantId);
    return response.toString();
  }

  public void emit(String restaurantId, OrderEvent orderEvent) {
    var emitter = emitters.get(restaurantId);
    logger.info("Trying to send event to restaurant: " + restaurantId);
    logger.info("Trying to send event to emitter: " + emitter);
    if (emitter != null) {
      try {
        emitter.send("New order received: " + orderEvent.getId());
        logger.info("Sent event to restaurant: " + restaurantId);
      } catch (Exception e) {
        emitters.remove(restaurantId);
      }
    }
  }

  public void addEmitter(String restaurantId, SseEmitter emitter) {
    emitters.put(restaurantId, emitter);
    emitter.onCompletion(() -> emitters.remove(restaurantId));
    emitter.onTimeout(() -> emitters.remove(restaurantId));
    emitter.onError((e) -> emitters.remove(restaurantId));
    logger.info("Added emitter for restaurant: " + restaurantId);
  }

  public void finishOrder(String orderId) {
    logger.info("Trying to finish order: " + orderId);
    var request = UpdateOrderStatusRequest.newBuilder()
        .setOrderId(orderId)
        .setStatus("ORDER_READY")
        .build();
    var response = orderManagementService.updateOrderStatus(request);
    logger.info("Finished order: " + response.getId());
  }
}

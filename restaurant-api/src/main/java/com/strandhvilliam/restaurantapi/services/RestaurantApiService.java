package com.strandhvilliam.restaurantapi.services;

import com.strandhvilliam.orderevent.proto.OrderEvent;
import com.strandhvilliam.ordermanagement.grpc.*;
import com.strandhvilliam.restaurantapi.utils.CustomLogger;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RestaurantApiService {

  private static final String ORDER_MANAGEMENT_CLIENT = "order-management";

  private final CustomLogger logger;

  private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

  @GrpcClient(ORDER_MANAGEMENT_CLIENT)
  private OrderManagementServiceGrpc.OrderManagementServiceBlockingStub orderManagementService;

  public RestaurantApiService(CustomLogger logger) {
    this.logger = logger;
  }

  public List<String> getRestaurantOrders(String restaurantId) {
    logger.info("Trying to get orders for restaurant: " + restaurantId, RestaurantApiService.class.getSimpleName());
    var request = GetRestaurantOrdersRequest.newBuilder()
        .setRestaurantId(restaurantId)
        .build();
    var response = orderManagementService.getRestaurantOrders(request);


    var filtered = response.getOrdersList().stream()
        .filter(orderResponse ->
            orderResponse.getStatus().equals("ORDER_CREATED") ||
                orderResponse.getStatus().equals("ORDER_READY")
        )
        .toList();
    return filtered.stream()
        .map(res -> "{ \"id\":\"" + res.getId() + "\",\"status\":\"" + res.getStatus() + "\"}").toList();
  }

  public void emit(String restaurantId, OrderEvent orderEvent) {
    var emitter = emitters.get(restaurantId);
    logger.info("Trying to send event to restaurant: " + restaurantId, RestaurantApiService.class.getSimpleName());
    logger.info("Trying to send event to emitter: " + emitter, RestaurantApiService.class.getSimpleName());
    if (emitter != null) {
      try {
        String json = "{ \"id\":\"" + orderEvent.getId() + "\",\"status\":\"" + orderEvent.getStatus() + "\"}";
        emitter.send(json);
        logger.info("Sent event to restaurant: " + restaurantId, RestaurantApiService.class.getSimpleName());
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
    logger.info("Added emitter for restaurant: " + restaurantId, RestaurantApiService.class.getSimpleName());
  }

  public String finishOrder(String orderId) {
    logger.info("Trying to finish order: " + orderId, RestaurantApiService.class.getSimpleName());
    var request = UpdateOrderStatusRequest.newBuilder()
        .setOrderId(orderId)
        .setStatus("ORDER_READY")
        .build();
    var response = orderManagementService.updateOrderStatus(request);
    logger.info("Finished order: " + response.getId(), RestaurantApiService.class.getSimpleName());
    return response.getId();
  }
}

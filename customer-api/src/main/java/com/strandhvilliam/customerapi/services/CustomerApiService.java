package com.strandhvilliam.customerapi.services;


import com.strandhvilliam.customerapi.models.OrderDto;
import com.strandhvilliam.customerapi.utils.CustomLogger;
import com.strandhvilliam.geolocevent.proto.GeoLocEvent;
import com.strandhvilliam.orderevent.proto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CustomerApiService {
  private final OrderManagementClient orderManagementClient;

  private final CustomLogger log;

  private final Map<String, SseEmitter> orderEmitters = new ConcurrentHashMap<>();
  private final Map<String, SseEmitter> geoLocEmitters = new ConcurrentHashMap<>();

  public CustomerApiService(OrderManagementClient orderManagementClient, CustomLogger log) {
    this.orderManagementClient = orderManagementClient;
    this.log = log;
  }

  public String createOrder(OrderDto dto) {
    var response = orderManagementClient.createOrder(dto);
    log.info("Order created with id: {}", response.getId());
    return response.getId();
  }

  public void emitOrderEvent(String customerId, OrderEvent orderEvent, String topic) {
    String type = "order-event";
    var emitter = orderEmitters.get(customerId);
    String json = "{ \"event\":\"" + type + "\",\"id\":\"" + orderEvent.getId() + "\",\"customerId\":\"" + orderEvent.getCustomerId() + "\",\"status\":\"" + orderEvent.getStatus() + "\"}";
    log.info("Trying to send event to customer: " + customerId, CustomerApiService.class.getSimpleName());
    log.info("Trying to send event to emitter: " + emitter, CustomerApiService.class.getSimpleName());
    if (emitter != null) {
      try {
        emitter.send(json);
        log.info("Sent event to customer: " + customerId + " with topic: " + topic, CustomerApiService.class.getSimpleName());
      } catch (Exception e) {
        orderEmitters.remove(customerId);
      }
    }
  }

  public void emitGeoLocEvent(String customerId, GeoLocEvent geoLocEvent) {
    String type = "geo-event";
    log.info("Trying to send geo location event to customer: " + geoLocEvent.getCustomerId(), CustomerApiService.class.getSimpleName());
    var emitter = geoLocEmitters.get(customerId);
    String json = "{\"event\":\"" + type +  "\",\"latitude\":" + geoLocEvent.getCoordinates().getLatitude() + ",\"longitude\":" + geoLocEvent.getCoordinates().getLongitude() + ",\"distance\":" + geoLocEvent.getDistance() + "}";
    log.info("Trying to send geo location event to customer: " + customerId, CustomerApiService.class.getSimpleName());
    log.info("Trying to send geo location event to emitter: " + emitter, CustomerApiService.class.getSimpleName());
    if (emitter != null) {
      try {
        emitter.send(json);
        log.info("Sent geo location event to customer: " + customerId, CustomerApiService.class.getSimpleName());
      } catch (Exception e) {
        orderEmitters.remove(customerId);
      }
    }
  }

  public SseEmitter connectOrderEmitter(String customerId) {
    log.info("Trying to connect emitter for customer: " + customerId, CustomerApiService.class.getSimpleName());
    if (orderEmitters.containsKey(customerId)) {
      log.info("Emitter already exists for customer: " + customerId, CustomerApiService.class.getSimpleName());
      return orderEmitters.get(customerId);
    }
    var emitter = new SseEmitter(Long.MAX_VALUE);
    orderEmitters.put(customerId, emitter);
    emitter.onCompletion(() -> orderEmitters.remove(customerId));
    emitter.onTimeout(() -> orderEmitters.remove(customerId));
    emitter.onError((e) -> orderEmitters.remove(customerId));
    log.info("Added emitter for customer: " + customerId, CustomerApiService.class.getSimpleName());
    return emitter;
  }

  public SseEmitter connectGeoLocEmitter(String customerId) {
    log.info("Trying to connect emitter for customer: " + customerId, CustomerApiService.class.getSimpleName());
    if (geoLocEmitters.containsKey(customerId)) {
      log.info("Emitter already exists for customer: " + customerId, CustomerApiService.class.getSimpleName());
      return geoLocEmitters.get(customerId);
    }
    var emitter = new SseEmitter(Long.MAX_VALUE);
    geoLocEmitters.put(customerId, emitter);
    emitter.onCompletion(() -> geoLocEmitters.remove(customerId));
    emitter.onTimeout(() -> geoLocEmitters.remove(customerId));
    emitter.onError((e) -> geoLocEmitters.remove(customerId));
    log.info("Added emitter for customer: " + customerId, CustomerApiService.class.getSimpleName());
    return emitter;
  }

  public List<String> getOrders(String customerId) {
    log.info("Trying to get orders for customer: " + customerId, CustomerApiService.class.getSimpleName());
    var response = orderManagementClient.getOrders(customerId);
    log.info("Got orders for customer: " + customerId, CustomerApiService.class.getSimpleName());
    return response.getOrdersList().stream()
        .map(res -> {
          log.info("Order id: " + res.getId(), CustomerApiService.class.getSimpleName());
          return "{ \"id\":\"" + res.getId() + "\",\"customerId\":\"" + res.getCustomerId() + "\",\"status\":\"" + res.getStatus() + "\"}";
        })
        .toList();
  }
}

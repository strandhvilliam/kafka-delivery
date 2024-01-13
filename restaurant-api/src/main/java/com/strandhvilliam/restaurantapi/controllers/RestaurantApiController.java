package com.strandhvilliam.restaurantapi.controllers;

import com.strandhvilliam.ordermanagement.grpc.ListOrderResponses;
import com.strandhvilliam.restaurantapi.services.RestaurantApiService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class RestaurantApiController {

  private final RestaurantApiService restaurantApiService;

  public RestaurantApiController(RestaurantApiService restaurantApiService) {
    this.restaurantApiService = restaurantApiService;
  }

  @PostMapping("/order/{orderId}")
  @CrossOrigin(origins = "*")
  public void finishOrder(@PathVariable String orderId) {
    restaurantApiService.finishOrder(orderId);
  }

  @GetMapping(path = "/orders/sse/{restaurantId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  @CrossOrigin(origins = "*")
  public SseEmitter subscribe(@PathVariable String restaurantId) {
    var emitter = new SseEmitter(Long.MAX_VALUE);
    restaurantApiService.addEmitter(restaurantId, emitter);
    return emitter;
  }

  @GetMapping("/orders/{restaurantId}")
  @CrossOrigin(origins = "*")
  public String getOrders(@PathVariable String restaurantId) {
    return restaurantApiService.getRestaurantOrders(restaurantId);
  }

}

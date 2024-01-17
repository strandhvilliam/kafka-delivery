package com.strandhvilliam.analyticsservice.controllers;

import com.strandhvilliam.analyticsservice.services.OrderAnalyticsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyticsController {

  private final OrderAnalyticsService orderAnalyticsService;

  public AnalyticsController(OrderAnalyticsService orderAnalyticsService) {
    this.orderAnalyticsService = orderAnalyticsService;
  }

  @GetMapping("average-order-value/{restaurantId}")
  @CrossOrigin(origins = "*")
  public double getAverageOrderValue(@PathVariable String restaurantId) {
    return orderAnalyticsService.getAverageOrderValue(restaurantId);
  }

  @GetMapping("average-sold-product-price/{restaurantId}")
  @CrossOrigin(origins = "*")
  public double getAverageSoldProductPrice(@PathVariable String restaurantId) {
    return orderAnalyticsService.getAverageSoldProductPrice(restaurantId);
  }

  @GetMapping("average-total-delivery-time/{restaurantId}")
  @CrossOrigin(origins = "*")
  public double getAverageTotalDeliveryTime(@PathVariable String restaurantId) {
    return orderAnalyticsService.getAverageTotalDeliveryTime(restaurantId);
  }

  @GetMapping("average-pickup-to-delivered-time/{driverId}")
  @CrossOrigin(origins = "*")
  public double getAveragePickupToDeliveredTime(@PathVariable String driverId) {

    return orderAnalyticsService.getAveragePickupToDeliveredTime(driverId);
  }

  @GetMapping("average-finished-to-pickup-time/{driverId}")
  @CrossOrigin(origins = "*")
  public double getAverageFinishedToPickupTime(@PathVariable String driverId) {
    return orderAnalyticsService.getAverageFinishedToPickupTime(driverId);
  }

  @GetMapping("average-restaurant-finish-time/{restaurantId}")
  @CrossOrigin(origins = "*")
  public double getAverageRestaurantFinishTime(@PathVariable String restaurantId) {
    return orderAnalyticsService.getAverageRestaurantFinishTime(restaurantId);
  }

  @GetMapping("most-popular-product/{restaurantId}")
  @CrossOrigin(origins = "*")
  public String getMostPopularProduct(@PathVariable String restaurantId) {
    return orderAnalyticsService.getMostPopularProduct(restaurantId);
  }

}

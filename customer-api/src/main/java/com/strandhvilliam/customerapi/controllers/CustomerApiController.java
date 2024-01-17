package com.strandhvilliam.customerapi.controllers;

import com.strandhvilliam.customerapi.services.CustomerApiService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.strandhvilliam.customerapi.models.OrderDto;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CustomerApiController {

  private final CustomerApiService customerApiService;

  public CustomerApiController(CustomerApiService customerApiService) {
    this.customerApiService = customerApiService;
  }

  @GetMapping("/orders/{customerId}")
  @CrossOrigin(origins = "*")
  public List<String> getOrders(@PathVariable String customerId) {
    return customerApiService.getOrders(customerId);
  }

  @GetMapping(path = "/orders/sse/{customerId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  @CrossOrigin(origins = "*")
  public SseEmitter subscribe(@PathVariable String customerId) {
    return customerApiService.connectOrderEmitter(customerId);
  }

  @GetMapping(path = "/geolocation/sse/{customerId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  @CrossOrigin(origins = "*")
  public SseEmitter subscribeGeoLoc(@PathVariable String customerId) {
    return customerApiService.connectGeoLocEmitter(customerId);
  }

  @PostMapping("/order")
  public String createOrder(@RequestBody @Valid OrderDto dto) {
    return customerApiService.createOrder(dto);
  }


}

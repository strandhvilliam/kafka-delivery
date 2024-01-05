package com.strandhvilliam.customerapi;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.strandhvilliam.customerapi.order.OrderDto;
import com.strandhvilliam.customerapi.order.Order;

@RestController
public class CustomerApiController {

  private final CustomerApiService customerApiService;

  public CustomerApiController(CustomerApiService customerApiService) {
    this.customerApiService = customerApiService;
  }

  @PostMapping("/orders")
  public Order createOrder(@RequestBody OrderDto dto) {
    // authenticate user
    return customerApiService.createOrder(dto);
  }

}

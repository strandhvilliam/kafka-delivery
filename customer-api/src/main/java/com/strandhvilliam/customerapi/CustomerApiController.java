package com.strandhvilliam.customerapi;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.strandhvilliam.customerapi.order.OrderDto;

@RestController
public class CustomerApiController {

  private final CustomerApiService customerApiService;

  public CustomerApiController(CustomerApiService customerApiService) {
    this.customerApiService = customerApiService;
  }

  @PostMapping("/order")
  public void createOrder(@RequestBody @Valid OrderDto dto) {
    customerApiService.createOrder(dto);
  }


}

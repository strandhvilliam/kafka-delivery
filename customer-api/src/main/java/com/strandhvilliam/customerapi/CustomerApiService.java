package com.strandhvilliam.customerapi;


import com.strandhvilliam.customerapi.order.OrderDto;
import com.strandhvilliam.customerapi.order.OrderManagementClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CustomerApiService {
  private final Logger log = LoggerFactory.getLogger(CustomerApiService.class.getSimpleName());
  private final OrderManagementClient orderManagementClient;

  public CustomerApiService(OrderManagementClient orderManagementClient) {
    this.orderManagementClient = orderManagementClient;
  }

  public void createOrder(OrderDto dto) {
    var response = orderManagementClient.createOrder(dto);
    log.info("Order created with id: {}", response.getId());
  }


}

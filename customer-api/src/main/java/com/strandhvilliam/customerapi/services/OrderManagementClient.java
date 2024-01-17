package com.strandhvilliam.customerapi.services;

import com.strandhvilliam.customerapi.models.OrderDto;
import com.strandhvilliam.ordermanagement.grpc.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderManagementClient {

  private static final String ORDER_MANAGEMENT_CLIENT = "order-management";

  private final Logger log = LoggerFactory.getLogger(OrderManagementClient.class.getSimpleName());

  @GrpcClient(ORDER_MANAGEMENT_CLIENT)
  private OrderManagementServiceGrpc.OrderManagementServiceBlockingStub orderManagementServiceStub;

  public OrderResponse createOrder(OrderDto dto) {
    log.info("Sending request to create order");
    CreateOrderRequest request = buildRequest(dto);
    return orderManagementServiceStub.createOrder(request);
  }

  public ListOrderResponses getOrders(String customerId) {
    log.info("Sending request to get orders");
    var request = GetCustomerOrdersRequest.newBuilder()
        .setCustomerId(customerId)
        .build();
    return orderManagementServiceStub.getCustomerOrders(request);
  }

  private CreateOrderRequest buildRequest(OrderDto dto) {

    return CreateOrderRequest.newBuilder()
        .addAllProductIds(dto.getProductIds())
        .setCustomerId(dto.getCustomerId())
        .build();
  }


}

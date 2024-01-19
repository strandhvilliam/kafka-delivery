package com.strandhvilliam.customerapi.services;

import com.strandhvilliam.customerapi.models.OrderDto;
import com.strandhvilliam.customerapi.utils.CustomLogger;
import com.strandhvilliam.ordermanagement.grpc.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderManagementClient {

  private static final String ORDER_MANAGEMENT_CLIENT = "order-management";

  private final CustomLogger log;

  @GrpcClient(ORDER_MANAGEMENT_CLIENT)
  private OrderManagementServiceGrpc.OrderManagementServiceBlockingStub orderManagementServiceStub;

  public OrderManagementClient(CustomLogger log) {
    this.log = log;
  }

  public OrderResponse createOrder(OrderDto dto) {
    log.info("Sending request to create order", OrderManagementClient.class.getSimpleName());
    CreateOrderRequest request = buildRequest(dto);
    return orderManagementServiceStub.createOrder(request);
  }

  public ListOrderResponses getOrders(String customerId) {
    log.info("Sending request to get orders", OrderManagementClient.class.getSimpleName());
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

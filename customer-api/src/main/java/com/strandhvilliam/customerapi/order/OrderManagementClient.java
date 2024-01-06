package com.strandhvilliam.customerapi.order;

import com.strandhvilliam.ordermanagement.grpc.CreateOrderRequest;
import com.strandhvilliam.ordermanagement.grpc.OrderManagementServiceGrpc;
import com.strandhvilliam.ordermanagement.grpc.OrderResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderManagementClient {

  private final Logger log = LoggerFactory.getLogger(OrderManagementClient.class.getSimpleName());

  @GrpcClient("order_management")
  private OrderManagementServiceGrpc.OrderManagementServiceBlockingStub orderManagementServiceStub;

  public OrderResponse createOrder(OrderDto dto) {
    log.info("Sending request to create order");
    CreateOrderRequest request = buildRequest(dto);
    return orderManagementServiceStub.createOrder(request);
  }

  private CreateOrderRequest buildRequest(OrderDto dto) {

    return CreateOrderRequest.newBuilder()
        .addAllProductIds(dto.getProductIds())
        .setUserId(dto.getUserId())
        .build();
  }


}

package com.strandhvilliam.ordermanagement.order;

import com.strandhvilliam.ordermanagement.grpc.CreateOrderRequest;
import com.strandhvilliam.ordermanagement.grpc.OrderItem;
import com.strandhvilliam.ordermanagement.grpc.OrderManagementServiceGrpc;
import com.strandhvilliam.ordermanagement.grpc.OrderResponse;
import com.strandhvilliam.ordermanagement.product.ProductModel;
import io.grpc.stub.StreamObserver;

import com.strandhvilliam.ordermanagement.product.ProductClient;

import net.devh.boot.grpc.server.service.GrpcService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@GrpcService
public class OrderService extends OrderManagementServiceGrpc.OrderManagementServiceImplBase {

  private static final String ORDER_STATUS_PROCESSING = "PROCESSING";

  private final ProductClient productClient;
  private final OrderProducer orderProducer;
  private final OrderRepository orderRepository;

  public OrderService(ProductClient productClient, OrderProducer orderProducer, OrderRepository orderRepository) {
    this.productClient = productClient;
    this.orderProducer = orderProducer;
    this.orderRepository = orderRepository;
  }

  @Override
  public void createOrder(
      CreateOrderRequest req,
      StreamObserver<OrderResponse> responseObserver) {

    var products = productClient.getManyProducts(req.getProductIdsList());
    var order = buildEntity(req, buildOrderItems(products));

    orderProducer.send(order);
    orderRepository.save(order);

    responseObserver.onNext(buildResponse(order));
    responseObserver.onCompleted();
  }

  private List<OrderItemEntity> buildOrderItems(List<ProductModel> products) {
    return products.stream().map(product -> {
      var orderItem = new OrderItemEntity();
      orderItem.setId(UUID.randomUUID().toString());
      orderItem.setProductId(product.getId());
      orderItem.setDescription(product.getDescription());
      orderItem.setCost(product.getCost());
      return orderItem;
    }).toList();
  }

  private OrderEntity buildEntity(CreateOrderRequest req, List<OrderItemEntity> orderItems) {
    var order = new OrderEntity();
    order.setId(UUID.randomUUID().toString());
    order.setDate(LocalDateTime.now().toString());
    order.setStatus(ORDER_STATUS_PROCESSING);
    order.setUserId(req.getUserId());
    order.setItems(orderItems);
    return order;
  }


  private OrderResponse buildResponse(OrderEntity order) {
    return OrderResponse.newBuilder()
        .setId(order.getId())
        .setStatus(order.getStatus())
        .setDate(order.getDate())
        .addAllItems(order.getItems().stream().map(orderItem -> OrderItem.newBuilder()
            .setId(orderItem.getId())
            .setProductId(orderItem.getProductId())
            .setDescription(orderItem.getDescription())
            .setCost(orderItem.getCost())
            .build()).toList())
        .build();
  }
}

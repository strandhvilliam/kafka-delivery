package com.strandhvilliam.ordermanagement.services;

import com.strandhvilliam.ordermanagement.grpc.*;
import com.strandhvilliam.ordermanagement.entities.OrderEntity;
import com.strandhvilliam.ordermanagement.entities.OrderItemEntity;
import com.strandhvilliam.ordermanagement.producers.OrderProducer;
import com.strandhvilliam.ordermanagement.repositories.OrderRepository;
import com.strandhvilliam.productcatalog.grpc.ListProductsResponse;
import io.grpc.stub.StreamObserver;

import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@GrpcService
public class OrderService extends OrderManagementServiceGrpc.OrderManagementServiceImplBase {

  private static final String ORDER_STATUS_PROCESSING = "PROCESSING";
  private final Logger log = LoggerFactory.getLogger(OrderService.class.getSimpleName());

  private final ProductClient productClient;
  private final OrderProducer orderProducer;
  private final OrderRepository orderRepository;

  public OrderService(ProductClient productClient, OrderProducer orderProducer, OrderRepository orderRepository) {
    this.productClient = productClient;
    this.orderProducer = orderProducer;
    this.orderRepository = orderRepository;
  }

  @Override
  public void getRestaurantOrders(
      GetRestaurantOrdersRequest req,
      StreamObserver<ListOrderResponses> responseObserver) {
    var orders = orderRepository.findByRestaurantId(req.getRestaurantId());
    var response = ListOrderResponses.newBuilder()
        .addAllOrders(orders.stream().map(this::buildResponse).toList())
        .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }


  @Override
  public void createOrder(
      CreateOrderRequest req,
      StreamObserver<OrderResponse> responseObserver) {

    var products = productClient.getManyProducts(req.getProductIdsList());
    // TODO: check if products are from the same restaurant
    var restaurantId = products.getProductsList()
        .isEmpty() ? "" : products.getProductsList().get(0).getRestaurantId();
    var order = buildEntity(req, restaurantId);
    var orderItems = buildOrderItems(products, order);
    order.setItems(orderItems);

    orderRepository.save(order);
    orderProducer.send(order);

    responseObserver.onNext(buildResponse(order));
    responseObserver.onCompleted();
  }

  private List<OrderItemEntity> buildOrderItems(ListProductsResponse products, OrderEntity order) {
    return products.getProductsList().stream().map(product -> OrderItemEntity
        .builder()
        .id(UUID.randomUUID().toString())
        .productId(product.getId())
        .description(product.getDescription())
        .order(order)
        .cost(product.getCost())
        .build()).toList();
  }

  private OrderEntity buildEntity(CreateOrderRequest req, String restaurantId) {
    return OrderEntity
        .builder()
        .id(UUID.randomUUID().toString())
        .date(LocalDateTime.now().toString())
        .status(ORDER_STATUS_PROCESSING)
        .restaurantId(restaurantId)
        .customerId(req.getCustomerId())
        .build();
  }


  private OrderResponse buildResponse(OrderEntity order) {
    return OrderResponse.newBuilder()
        .setId(order.getId())
        .setStatus(order.getStatus())
        .setDate(order.getDate())
        .addAllItems(order.getItems() == null
            ? List.of()
            : order.getItems().stream()
            .map(orderItem -> OrderItem.newBuilder()
                .setId(orderItem.getId())
                .setProductId(orderItem.getProductId())
                .setDescription(orderItem.getDescription())
                .setCost(orderItem.getCost())
                .build()).toList())
        .build();
  }
}

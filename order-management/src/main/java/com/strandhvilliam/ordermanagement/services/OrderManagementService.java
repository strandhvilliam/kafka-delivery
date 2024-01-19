package com.strandhvilliam.ordermanagement.services;

import com.strandhvilliam.ordermanagement.grpc.*;
import com.strandhvilliam.ordermanagement.entities.OrderEntity;
import com.strandhvilliam.ordermanagement.entities.OrderItemEntity;
import com.strandhvilliam.ordermanagement.producers.OrderProducer;
import com.strandhvilliam.ordermanagement.repositories.OrderRepository;
import com.strandhvilliam.ordermanagement.utils.CustomLogger;
import com.strandhvilliam.productcatalog.grpc.ListProductsResponse;
import io.grpc.stub.StreamObserver;

import net.devh.boot.grpc.server.service.GrpcService;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@GrpcService
public class OrderManagementService extends OrderManagementServiceGrpc.OrderManagementServiceImplBase {


  private final CustomLogger log;

  private final ProductClient productClient;
  private final OrderProducer orderProducer;
  private final OrderRepository orderRepository;

  public OrderManagementService(CustomLogger log, ProductClient productClient, OrderProducer orderProducer, OrderRepository orderRepository) {
    this.log = log;
    this.productClient = productClient;
    this.orderProducer = orderProducer;
    this.orderRepository = orderRepository;
  }

  @Override
  public void getOrder(GetOrderIdRequest req, StreamObserver<OrderResponse> responseObserver) {
    log.info("Getting order " + req.getOrderId(), OrderManagementService.class.getSimpleName());
    var order = orderRepository.findById(req.getOrderId()).orElseThrow();
    var response = buildResponse(order);

    log.info(String.format("Found order %s", req.getOrderId()), OrderManagementService.class.getSimpleName());
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

  @Override
  public void getManyOrders(GetManyOrdersRequest req, StreamObserver<ListOrderResponses> responsesStreamObserver) {
    log.info("Getting orders " + req.getOrderIdsList(), OrderManagementService.class.getSimpleName());
    var orders = orderRepository.findAllById(req.getOrderIdsList());
    var response = ListOrderResponses.newBuilder()
        .addAllOrders(orders.stream().map(this::buildResponse).toList())
        .build();

    log.info(String.format("Found %d orders", orders.size()), OrderManagementService.class.getSimpleName());
    responsesStreamObserver.onNext(response);
    responsesStreamObserver.onCompleted();
  }

  @Override
  public void getRestaurantOrders(
      GetRestaurantOrdersRequest req,
      StreamObserver<ListOrderResponses> responseObserver) {
    log.info(String.format("Getting orders for restaurant %s", req.getRestaurantId()), OrderManagementService.class.getSimpleName());
    var orders = orderRepository.findByRestaurantId(req.getRestaurantId());
    var response = ListOrderResponses.newBuilder()
        .addAllOrders(orders.stream().map(this::buildResponse).toList())
        .build();

    log.info(String.format("Found %d orders for restaurant %s", orders.size(), req.getRestaurantId()), OrderManagementService.class.getSimpleName());
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

  @Override
  public void getCustomerOrders(
      GetCustomerOrdersRequest req,
      StreamObserver<ListOrderResponses> responseObserver) {
    log.info("Getting orders for customer " + req.getCustomerId(), OrderManagementService.class.getSimpleName());
    var orders = orderRepository.findByCustomerId(req.getCustomerId());
    var response = ListOrderResponses.newBuilder()
        .addAllOrders(orders.stream().map(this::buildResponse).toList())
        .build();

    log.info(String.format("Found %d orders for customer %s", orders.size(), req.getCustomerId()), OrderManagementService.class.getSimpleName());
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

  @Override
  public void updateOrderStatus(UpdateOrderStatusRequest request, StreamObserver<OrderResponse> responseObserver) {
    log.info(String.format("Updating order %s status to %s", request.getOrderId(), request.getStatus()), OrderManagementService.class.getSimpleName());
    var order = orderRepository.findById(request.getOrderId()).orElseThrow();
    order.setStatus(request.getStatus());
    orderRepository.save(order);

    switch (request.getStatus()) {
      case "ORDER_READY":
        orderProducer.send("order_ready_dev", order);
        break;
      case "ORDER_DELIVERED":
        orderProducer.send("order_delivered_dev", order);
        break;
      case "ORDER_PICKED_UP":
        orderProducer.send("order_picked_up_dev", order);
        break;
      default:
        log.info(String.format("Order %s status not updated to %s", request.getOrderId(), request.getStatus()), OrderManagementService.class.getSimpleName());
        return;
    }

    log.info(String.format("Order %s status updated to %s", request.getOrderId(), request.getStatus()), OrderManagementService.class.getSimpleName());
    responseObserver.onNext(buildResponse(order));
    responseObserver.onCompleted();
  }

  @Override
  public void createOrder(
      CreateOrderRequest req,
      StreamObserver<OrderResponse> responseObserver) {
    log.info("Request: " + req.getProductIdsList(), OrderManagementService.class.getSimpleName());
    log.info("Creating order for customer: " + req.getCustomerId(), OrderManagementService.class.getSimpleName());
    log.info("Getting products " + req.getProductIdsList(), OrderManagementService.class.getSimpleName());
    var products = productClient.getManyProducts(req.getProductIdsList());

    if (products.getProductsList().isEmpty()) {
      log.info("No products found for ids " + req.getProductIdsList(), OrderManagementService.class.getSimpleName());
      throw new RuntimeException("No products found");
    }

    var restaurantId = products.getProductsList().get(0).getRestaurantId();

    // TODO: check if products are from the same restaurant

    var order = buildCreatedEntity(req, restaurantId);
    var orderItems = buildOrderItems(products, order);
    order.setItems(orderItems);

    log.info("Saving orderitems", OrderManagementService.class.getSimpleName());

    orderRepository.save(order);
    orderProducer.send("order_created_dev", order);

    log.info(String.format("Order %s created for customer %s", order.getId(), req.getCustomerId()), OrderManagementService.class.getSimpleName());

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

  private OrderEntity buildCreatedEntity(CreateOrderRequest req, String restaurantId) {
    return OrderEntity
        .builder()
        .id(UUID.randomUUID().toString())
        .createdAt(LocalDateTime.now().toString())
        .status("ORDER_CREATED")
        .restaurantId(restaurantId)
        .customerId(req.getCustomerId())
        .build();
  }


  private OrderResponse buildResponse(OrderEntity order) {
    return OrderResponse.newBuilder()
        .setId(order.getId())
        .setStatus(order.getStatus())
        .setCreatedAt(order.getCreatedAt())
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

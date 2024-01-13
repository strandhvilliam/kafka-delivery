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
    log.info("Getting orders for restaurant {}", req.getRestaurantId());
    var orders = orderRepository.findByRestaurantId(req.getRestaurantId());
    var response = ListOrderResponses.newBuilder()
        .addAllOrders(orders.stream().map(this::buildResponse).toList())
        .build();

    log.info("Found {} orders for restaurant {}", orders.size(), req.getRestaurantId());
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

  @Override
  public void updateOrderStatus(UpdateOrderStatusRequest request, StreamObserver<OrderResponse> responseObserver) {
    log.info("Updating order {} status to {}", request.getOrderId(), request.getStatus());
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
        break;
    }

    log.info("Order {} status updated to {}", request.getOrderId(), request.getStatus());
    responseObserver.onNext(buildResponse(order));
    responseObserver.onCompleted();
  }

  @Override
  public void createOrder(
      CreateOrderRequest req,
      StreamObserver<OrderResponse> responseObserver) {
    log.info("Creating order for customer {}", req.getCustomerId());
    var products = productClient.getManyProducts(req.getProductIdsList());
    // TODO: check if products are from the same restaurant
    var restaurantId = products.getProductsList()
        .isEmpty() ? "" : products.getProductsList().get(0).getRestaurantId();
    var order = buildCreatedEntity(req, restaurantId);
    var orderItems = buildOrderItems(products, order);
    order.setItems(orderItems);

    orderRepository.save(order);
    orderProducer.send("order_created_dev", order);

    log.info("Order {} created for customer {}", order.getId(), req.getCustomerId());

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
        .date(LocalDateTime.now().toString())
        .status("ORDER_CREATED")
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

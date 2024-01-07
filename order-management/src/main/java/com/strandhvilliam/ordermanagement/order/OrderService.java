package com.strandhvilliam.ordermanagement.order;

import com.strandhvilliam.ordermanagement.grpc.CreateOrderRequest;
import com.strandhvilliam.ordermanagement.grpc.OrderItem;
import com.strandhvilliam.ordermanagement.grpc.OrderManagementServiceGrpc;
import com.strandhvilliam.ordermanagement.grpc.OrderResponse;
import com.strandhvilliam.productcatalog.grpc.ListProductsResponse;
import io.grpc.stub.StreamObserver;

import com.strandhvilliam.ordermanagement.product.ProductClient;

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
    return products.getProductsList().stream().map(product -> new OrderItemEntity
        .OrderItemEntityBuilder()
        .id(UUID.randomUUID().toString())
        .productId(product.getId())
        .description(product.getDescription())
        .order(order)
        .cost(product.getCost())
        .build()).toList();
  }

  private OrderEntity buildEntity(CreateOrderRequest req, String restaurantId) {
    return new OrderEntity
        .OrderEntityBuilder()
        .id(UUID.randomUUID().toString())
        .date(LocalDateTime.now().toString())
        .status(ORDER_STATUS_PROCESSING)
        .restaurantId(restaurantId)
        .userId(req.getUserId())
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

package com.strandhvilliam.analyticsservice.services;

import com.strandhvilliam.analyticsservice.entities.*;
import com.strandhvilliam.analyticsservice.repositories.*;
import com.strandhvilliam.driveravailability.grpc.DriverServiceGrpc;
import com.strandhvilliam.driveravailability.grpc.OrderIdRequest;
import com.strandhvilliam.orderevent.proto.OrderEvent;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

@Service
public class OrderAnalyticsService {

  private static final String DRIVER_AVAILABILITY_SERVICE = "driver-availability";

  @GrpcClient(DRIVER_AVAILABILITY_SERVICE)
  private DriverServiceGrpc.DriverServiceBlockingStub driverAvailabilityServiceBlockingStub;

  private final OrderValueRepository orderValueRepository;
  private final SoldProductRepository soldProductRepository;
  private final RestaurantFinishTimeRepository restaurantFinishTimeRepository;
  private final PickupToDeliveredTimeRepository pickupToDeliveredTimeRepository;
  private final TotalDeliveredTimeRepository totalDeliveredTimeRepository;
  private final FinishedToPickupTimeRepository finishedToPickupTimeRepository;


  public OrderAnalyticsService(OrderValueRepository orderValueRepository, SoldProductRepository soldProductRepository, RestaurantFinishTimeRepository restaurantFinishTimeRepository, PickupToDeliveredTimeRepository pickupToDeliveredTimeRepository, TotalDeliveredTimeRepository totalDeliveredTimeRepository, FinishedToPickupTimeRepository finishedToPickupTimeRepository) {
    this.orderValueRepository = orderValueRepository;
    this.soldProductRepository = soldProductRepository;
    this.restaurantFinishTimeRepository = restaurantFinishTimeRepository;
    this.pickupToDeliveredTimeRepository = pickupToDeliveredTimeRepository;
    this.totalDeliveredTimeRepository = totalDeliveredTimeRepository;
    this.finishedToPickupTimeRepository = finishedToPickupTimeRepository;
  }

  public double getAverageOrderValue(String restaurantId) {
    var orderValues = orderValueRepository.findAllByRestaurantId(restaurantId);
    var sum = orderValues.stream()
        .mapToDouble(OrderValueEntity::getOrderValue)
        .sum();
    return orderValues.isEmpty() ? 0 :(sum / orderValues.size());
  }

  public double getAverageSoldProductPrice(String restaurantId) {
    var soldProducts = soldProductRepository.findAllByRestaurantId(restaurantId);
    var sum = soldProducts.stream()
        .mapToDouble(SoldProductEntity::getProductPrice)
        .sum();
    return soldProducts.isEmpty() ? 0 : (sum / soldProducts.size());
  }

  public double getAverageTotalDeliveryTime(String restaurantId) {
    var deliveryTimes = totalDeliveredTimeRepository.findAllByRestaurantId(restaurantId);
    var sum = deliveryTimes.stream()
        .mapToDouble(TotalDeliveredTimeEntity::getTotalDeliverySeconds)
        .sum();
    return deliveryTimes.isEmpty() ? 0 : (sum / deliveryTimes.size());
  }

  public double getAverageFinishedToPickupTime(String driverId) {
    var finishTimes = finishedToPickupTimeRepository.findAllByDriverId(driverId);
    var sum = finishTimes.stream()
        .mapToDouble(FinishedToPickupTimeEntity::getFinishedToPickupSeconds)
        .sum();

    return finishTimes.isEmpty() ? 0 : (sum / finishTimes.size());
  }

  public String getMostPopularProduct(String restaurantId) {
    var soldProducts = soldProductRepository.findAllByRestaurantId(restaurantId);
    var mostPopular = soldProducts.stream()
        .max(Comparator.comparingInt(SoldProductEntity::getQuantity))
        .orElseThrow();
    return mostPopular.getProductName() + " (" + mostPopular.getQuantity() + " sold)";
  }

  public double getAverageRestaurantFinishTime(String restaurantId) {
    var finishTimes = restaurantFinishTimeRepository.findAllByRestaurantId(restaurantId);
    var sum = finishTimes.stream()
        .mapToDouble(RestaurantFinishTimeEntity::getSecondsToFinish)
        .sum();
    return finishTimes.isEmpty() ? 0 : (sum / finishTimes.size());
  }

  public double getAveragePickupToDeliveredTime(String driverId) {
    var deliveryTimes = pickupToDeliveredTimeRepository.findAllByDriverId(driverId);
    var sum = deliveryTimes.stream()
        .mapToDouble(PickupToDeliveredTimeEntity::getPickupToDeliveredSeconds)
        .sum();
    return deliveryTimes.isEmpty() ? 0 : (sum / deliveryTimes.size());
  }

  public void processOrderPickedUpEvent(OrderEvent orderEvent) {
    var finished = restaurantFinishTimeRepository.findById(orderEvent.getId());
    if (finished.isPresent()) {
      var createdAt = LocalDateTime.parse(orderEvent.getCreatedAt());
      var finishedSeconds = finished.get().getSecondsToFinish() + createdAt.until(LocalDateTime.now(), ChronoUnit.SECONDS);
      insertFinishedToPickup(orderEvent, finishedSeconds);
    }
  }

  public void processOrderDeliveredEvent(OrderEvent orderEvent) {

    var pickup = finishedToPickupTimeRepository.findById(orderEvent.getId());
    if (pickup.isPresent()) {
      var createdAt = LocalDateTime.parse(orderEvent.getCreatedAt());
      var seconds = createdAt.until(LocalDateTime.now(), ChronoUnit.SECONDS);
      var pickupSeconds = pickup.get().getFinishedToPickupSeconds() + createdAt.until(LocalDateTime.now(), ChronoUnit.SECONDS);
      insertPickupToDeliveryEntity(orderEvent, pickupSeconds);
      insertTotalDeliveredTime(orderEvent, seconds);
    }
  }


  public void processOrderCreatedEvent(OrderEvent orderEvent) {
    insertSoldProduct(orderEvent);
    insertOrderValue(orderEvent);
  }

  public void processOrderFinishedEvent(OrderEvent orderEvent) {
    var createdAt = LocalDateTime.parse(orderEvent.getCreatedAt());
    var seconds = createdAt.until(LocalDateTime.now(), ChronoUnit.SECONDS);
    insertRestaurantFinishOrderTime(orderEvent, seconds);
  }

  private void insertFinishedToPickup(OrderEvent orderEvent, long seconds) {
    var job = driverAvailabilityServiceBlockingStub
        .getJobByOrderId(OrderIdRequest.newBuilder()
            .setOrderId(orderEvent.getId())
            .build());

    var finishedToPickupTimeEntity = FinishedToPickupTimeEntity.builder()
        .orderId(orderEvent.getId())
        .driverId(job.getDriverId())
        .restaurantId(orderEvent.getRestaurantId())
        .finishedToPickupSeconds(seconds)
        .build();

    finishedToPickupTimeRepository.save(finishedToPickupTimeEntity);
  }

  private void insertTotalDeliveredTime(OrderEvent orderEvent, long seconds) {
    var job = driverAvailabilityServiceBlockingStub
        .getJobByOrderId(OrderIdRequest.newBuilder()
            .setOrderId(orderEvent.getId())
            .build());

    var entity = TotalDeliveredTimeEntity.builder()
        .orderId(orderEvent.getId())
        .driverId(job.getDriverId())
        .restaurantId(orderEvent.getRestaurantId())
        .totalDeliverySeconds(seconds)
        .build();

    totalDeliveredTimeRepository.save(entity);
  }

  private void insertPickupToDeliveryEntity(OrderEvent orderEvent, long seconds) {
    var job = driverAvailabilityServiceBlockingStub
        .getJobByOrderId(OrderIdRequest.newBuilder()
            .setOrderId(orderEvent.getId())
            .build());

    var pickupToDeliveredTimeEntity = PickupToDeliveredTimeEntity.builder()
        .orderId(orderEvent.getId())
        .driverId(job.getDriverId())
        .pickupToDeliveredSeconds(seconds)
        .build();

    pickupToDeliveredTimeRepository.save(pickupToDeliveredTimeEntity);
  }


  private void insertRestaurantFinishOrderTime(OrderEvent orderEvent, long seconds) {
    var finishTimeEntity = RestaurantFinishTimeEntity.builder()
        .orderId(orderEvent.getId())
        .restaurantId(orderEvent.getRestaurantId())
        .secondsToFinish(seconds)
        .build();

    restaurantFinishTimeRepository.save(finishTimeEntity);
  }


  private void insertSoldProduct(OrderEvent orderEvent) {
    orderEvent.getItemsList().forEach(item -> {
      var soldProductEntity = soldProductRepository.findById(item.getProductId());

      if (soldProductEntity.isPresent()) {
        var updated = soldProductEntity.get();
        updated.setQuantity(updated.getQuantity() + 1);
        soldProductRepository.save(soldProductEntity.get());
      } else {
        var newSoldProductEntity = SoldProductEntity.builder()
            .productId(item.getProductId())
            .quantity(1)
            .productName(item.getDescription())
            .restaurantId(orderEvent.getRestaurantId())
            .productPrice(item.getCost())
            .build();
        soldProductRepository.save(newSoldProductEntity);
      }
    });
  }

  private void insertOrderValue(OrderEvent orderEvent) {
    var value = orderEvent.getItemsList().stream()
        .reduce(0.0, (subtotal, item) -> subtotal + item.getCost(), Double::sum);

    var orderValueEntity = OrderValueEntity.builder()
        .orderId(orderEvent.getId())
        .orderValue(value)
        .restaurantId(orderEvent.getRestaurantId())
        .build();

    orderValueRepository.save(orderValueEntity);
  }


}

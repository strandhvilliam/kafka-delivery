package com.strandhvilliam.restaurantapi.consumers;

import com.strandhvilliam.orderevent.proto.OrderEvent;
import com.strandhvilliam.restaurantapi.services.RestaurantApiService;
import com.strandhvilliam.restaurantapi.utils.CustomLogger;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {
  private static final String GROUP_ID = "restaurant-api-group";

  private static final String CONSUMER_FACTORY = "orderEventListenerContainerFactory";

  private final CustomLogger logger;

  private final RestaurantApiService restaurantApiService;

  public OrderEventConsumer(CustomLogger logger, RestaurantApiService restaurantApiService) {
    this.logger = logger;
    this.restaurantApiService = restaurantApiService;
  }

  @KafkaListener(topics = {"order_created_dev", "order_picked_up_dev"}, groupId = GROUP_ID, containerFactory = CONSUMER_FACTORY)
  public void consume(ConsumerRecord<String, OrderEvent> event) {
    logger.info(String.format("Consumed event -> %s", event.value().getRestaurantId()), OrderEventConsumer.class.getSimpleName());
    restaurantApiService.emit(event.value().getRestaurantId(), event.value());
  }

}

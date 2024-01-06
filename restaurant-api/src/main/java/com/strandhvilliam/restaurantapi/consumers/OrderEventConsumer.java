package com.strandhvilliam.restaurantapi.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

  private final Logger logger = LoggerFactory.getLogger(OrderEventConsumer.class);

  @KafkaListener(topics = "order_created_dev", groupId = "restaurant-api-group")
  public void consume(String message) {
    logger.info(String.format("#### -> Consumed message -> %s", message));
  }

}

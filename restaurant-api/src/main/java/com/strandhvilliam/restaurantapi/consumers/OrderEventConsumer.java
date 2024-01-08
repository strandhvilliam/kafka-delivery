package com.strandhvilliam.restaurantapi.consumers;

import com.strandhvilliam.events.proto.OrderEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {
  private static final String TOPIC = "order_created_dev";
  private static final String GROUP_ID = "restaurant-api-group";

  private final Logger logger = LoggerFactory.getLogger(OrderEventConsumer.class);

  @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
  public void consume(ConsumerRecord<String, OrderEvent> event) {

    logger.info(String.format("#### -> Consumed message -> %s", event.value()));
  }

}

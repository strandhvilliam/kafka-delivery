package com.strandhvilliam.driveravailability.consumers;

import com.strandhvilliam.events.proto.OrderEvent;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

  private final Logger logger = LoggerFactory.getLogger(OrderEventConsumer.class);

  @KafkaListener(topics = "order_created_dev", groupId = "driver-availability-group")
  public void consume(ConsumerRecord<String, OrderEvent> event) {
    logger.info("Consumed order event: {}", event.value().getId());
  }

}

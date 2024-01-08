package com.strandhvilliam.driveravailability.consumers;

import com.google.protobuf.DynamicMessage;
import com.strandhvilliam.driveravailability.services.DriverAvailabilityService;
import com.strandhvilliam.events.proto.OrderEvent;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

  private static final String TOPIC = "order_created_dev";
  private static final String GROUP_ID = "driver-availability-group";

  private final Logger logger = LoggerFactory.getLogger(OrderEventConsumer.class.getSimpleName());

  private final DriverAvailabilityService driverAvailabilityService;

  public OrderEventConsumer(DriverAvailabilityService driverAvailabilityService) {
    this.driverAvailabilityService = driverAvailabilityService;
  }

  @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
  public void consume(ConsumerRecord<String, OrderEvent> event) {
    logger.info("Consumed order event: {}", event.value());
    driverAvailabilityService.assignDriver(event.value());
  }

}

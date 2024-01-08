package com.strandhvilliam.driveravailability.consumers;

import com.strandhvilliam.driveravailability.services.DriverAvailabilityService;
import com.strandhvilliam.events.proto.OrderEvent;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

  private final Logger logger = LoggerFactory.getLogger(OrderEventConsumer.class);

  private final DriverAvailabilityService driverAvailabilityService;

  public OrderEventConsumer(DriverAvailabilityService driverAvailabilityService) {
    this.driverAvailabilityService = driverAvailabilityService;
  }

  @KafkaListener(topics = "order_created_dev", groupId = "driver-availability-group")
  public void consume(ConsumerRecord<String, OrderEvent> event) {
    logger.info("Consumed order event: {}", event.value().getId());
    driverAvailabilityService.assignDriver(event.value());
  }

}

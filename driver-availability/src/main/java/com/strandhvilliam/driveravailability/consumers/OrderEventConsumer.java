package com.strandhvilliam.driveravailability.consumers;

import com.strandhvilliam.driveravailability.services.AvailabilityService;
import com.strandhvilliam.driveravailability.utils.CustomLogger;
import com.strandhvilliam.orderevent.proto.OrderEvent;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

  private static final String TOPIC = "order_created_dev";
  private static final String GROUP_ID = "driver-availability-group";

  private final CustomLogger log;
  private final AvailabilityService availabilityService;

  public OrderEventConsumer(CustomLogger log, AvailabilityService availabilityService) {
    this.log = log;
    this.availabilityService = availabilityService;
  }

  @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
  public void consume(ConsumerRecord<String, OrderEvent> event) {
    log.info("Consumed order event: " + event.value().getId(), OrderEventConsumer.class.getSimpleName());
    availabilityService.assignDriver(event.value());
  }

}

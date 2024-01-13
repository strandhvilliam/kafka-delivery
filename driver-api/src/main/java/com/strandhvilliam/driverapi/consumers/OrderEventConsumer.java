package com.strandhvilliam.driverapi.consumers;

import com.strandhvilliam.driverapi.services.DriverApiService;
import com.strandhvilliam.orderevent.proto.OrderEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.strandhvilliam.driverapi.config.KafkaConsumerConfig.GROUP_ID;

@Component
public class OrderEventConsumer {
  private final Logger logger = LoggerFactory.getLogger(OrderEventConsumer.class.getSimpleName());

  private final DriverApiService driverApiService;

  public OrderEventConsumer(DriverApiService driverApiService) {
    this.driverApiService = driverApiService;
  }

  @KafkaListener(topics = "order_ready_dev", groupId = GROUP_ID, containerFactory = "orderEventListenerContainerFactory")
  public void consume(ConsumerRecord<String, OrderEvent> event) {
    logger.info(String.format("#### -> Consumed order event -> %s", event.value()));
    driverApiService.sendEvent(event.value().getId(), event.value().getStatus(), "order_ready");
  }





}

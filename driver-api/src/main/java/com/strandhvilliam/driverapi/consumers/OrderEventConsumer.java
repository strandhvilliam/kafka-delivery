package com.strandhvilliam.driverapi.consumers;

import com.strandhvilliam.driverapi.services.DriverApiService;
import com.strandhvilliam.driverapi.utils.CustomLogger;
import com.strandhvilliam.orderevent.proto.OrderEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.strandhvilliam.driverapi.config.KafkaConsumerConfig.GROUP_ID;

@Component
public class OrderEventConsumer {

  private final CustomLogger logger;

  private final DriverApiService driverApiService;

  public OrderEventConsumer(CustomLogger logger, DriverApiService driverApiService) {
    this.logger = logger;
    this.driverApiService = driverApiService;
  }

  @KafkaListener(topics = {"order_ready_dev", "order_created_dev", "order_picked_up_dev", "order_delivered_dev"}, groupId = GROUP_ID, containerFactory = "orderEventListenerContainerFactory")
  public void consume(ConsumerRecord<String, OrderEvent> event) {
    logger.info(String.format("#### -> Consumed order event -> %s", event.value().getId()), OrderEventConsumer.class.getSimpleName());
    driverApiService.emitOrderEvent(event.value());
  }





}

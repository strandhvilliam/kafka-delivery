package com.strandhvilliam.customerapi.consumers;

import com.strandhvilliam.customerapi.services.CustomerApiService;
import com.strandhvilliam.customerapi.utils.CustomLogger;
import com.strandhvilliam.orderevent.proto.OrderEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


@Component
public class OrderEventConsumer {

  public static final String GROUP_ID = "customer-api-group";

  private final CustomLogger log;

  private final CustomerApiService customerApiService;

  public OrderEventConsumer(CustomLogger log, CustomerApiService customerApiService) {
    this.log = log;
    this.customerApiService = customerApiService;
  }

  @KafkaListener(
      topics = {"order_picked_up_dev", "order_delivered_dev"},
      groupId = GROUP_ID,
      containerFactory = "orderEventListenerContainerFactory"
  )
  public void consume(ConsumerRecord<String, OrderEvent> event, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
    log.info(String.format("##### Consumed order event -> %s", event.value().getId()), OrderEventConsumer.class.getSimpleName());
    customerApiService.emitOrderEvent(event.value().getCustomerId(), event.value(), topic);
  }


}

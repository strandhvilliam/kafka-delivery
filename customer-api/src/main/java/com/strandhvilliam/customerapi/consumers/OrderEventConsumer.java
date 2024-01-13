package com.strandhvilliam.customerapi.consumers;

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
  private final Logger logger = LoggerFactory.getLogger(OrderEventConsumer.class.getSimpleName());

  @KafkaListener(
      topics = {"order_picked_up_dev", "order_delivered_dev"},
      groupId = GROUP_ID,
      containerFactory = "orderEventListenerContainerFactory"
  )
  public void consume(ConsumerRecord<String, OrderEvent> event, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
    logger.info(String.format("##### %n Consumed order event -> %s $n For topic -> %s %n #####", event.value().getId(), topic));
  }


}

package com.strandhvilliam.analyticsservice.consumers;


import com.strandhvilliam.analyticsservice.services.OrderAnalyticsService;
import com.strandhvilliam.orderevent.proto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

  private final Logger logger = LoggerFactory.getLogger(OrderEventConsumer.class);

  private final OrderAnalyticsService orderAnalyticsService;

  public OrderEventConsumer(OrderAnalyticsService orderAnalyticsService) {
    this.orderAnalyticsService = orderAnalyticsService;
  }

  @KafkaListener(
      topics = {
          "order_created_dev",
          "order_ready_dev",
          "order_picked_up_dev",
          "order_delivered_dev"
      },
      groupId = "analytics-service-group",
      containerFactory = "orderEventListenerContainerFactory"
  )
  public void consume(OrderEvent event, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

    logger.info("Received event {} from topic {}", event.getId(), topic);

    switch (topic) {
      case "order_created_dev":
        orderAnalyticsService.processOrderCreatedEvent(event);
        break;
      case "order_ready_dev":
        orderAnalyticsService.processOrderFinishedEvent(event);
        break;
      case "order_picked_up_dev":
        orderAnalyticsService.processOrderPickedUpEvent(event);
        break;
      case "order_delivered_dev":
        orderAnalyticsService.processOrderDeliveredEvent(event);
        break;
      default:
        logger.warn("Unknown topic {}", topic);
        break;
    }
  }
}

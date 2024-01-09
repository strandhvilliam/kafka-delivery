package com.strandhvilliam.ordermanagement.producers;

import com.strandhvilliam.events.proto.OrderEvent;
import com.strandhvilliam.events.proto.OrderEventItem;
import com.strandhvilliam.ordermanagement.entities.OrderEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderProducer {
  private static final String TOPIC = "order_created_dev";

  private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

  public OrderProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  /**
   * Sends an order event to the kafka topic.
   * The key is the order id, so the message will be sent to the same partition as the order id.
   *
   * @param entity the order entity
   */
  public void send(OrderEntity entity) {
    var orderEvent = OrderEvent.newBuilder()
        .setId(entity.getId())
        .setDate(entity.getDate())
        .setRestaurantId(entity.getRestaurantId())
        .setStatus(entity.getStatus())
        .setCustomerId(entity.getCustomerId())
        .addAllItems(entity.getItems() == null ? List.of() :
            entity.getItems().stream().map(item -> OrderEventItem.newBuilder()
                    .setId(item.getId())
                    .setProductId(item.getProductId())
                    .setDescription(item.getDescription())
                    .setCost(item.getCost())
                    .build())
                .toList())
        .build();

    kafkaTemplate.send(TOPIC, orderEvent.getId(), orderEvent);
  }


}

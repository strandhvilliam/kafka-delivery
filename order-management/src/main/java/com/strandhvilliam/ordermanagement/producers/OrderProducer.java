package com.strandhvilliam.ordermanagement.producers;

import com.strandhvilliam.orderevent.proto.OrderEvent;
import com.strandhvilliam.orderevent.proto.OrderEventItem;
import com.strandhvilliam.ordermanagement.entities.OrderEntity;
import com.strandhvilliam.ordermanagement.utils.CustomLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderProducer {

  private final CustomLogger log;
  @Qualifier("orderTemplate")
  private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

  public OrderProducer(CustomLogger log, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
    this.log = log;
    this.kafkaTemplate = kafkaTemplate;
  }

  /**
   * Sends an order event to the kafka topic.
   * The key is the order id, so the message will be sent to the same partition as the order id.
   *
   * @param entity the order entity
   */
  public void send(String topic, OrderEntity entity) {
    log.info(String.format("SENDING EVENT %s to topic %s", entity.getId(), topic), OrderProducer.class.getSimpleName());
    var orderEvent = OrderEvent.newBuilder()
        .setId(entity.getId())
        .setCreatedAt(entity.getCreatedAt())
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

    kafkaTemplate.send(topic , orderEvent.getId(), orderEvent);
  }


}

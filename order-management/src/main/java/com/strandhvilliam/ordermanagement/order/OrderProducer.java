package com.strandhvilliam.ordermanagement.order;

import com.strandhvilliam.events.proto.OrderEvent;
import com.strandhvilliam.events.proto.OrderEventItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderProducer {

  private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

  public OrderProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void send(OrderEntity entity) {
    var orderEvent = OrderEvent.newBuilder()
        .setId(entity.getId())
        .setDate(entity.getDate())
        .setRestaurantId(entity.getRestaurantId())
        .setStatus(entity.getStatus())
        .setUserId(entity.getUserId())
        .addAllItems(entity.getItems() == null ? List.of() :
            entity.getItems().stream().map(item -> OrderEventItem.newBuilder()
                    .setId(item.getId())
                    .setProductId(item.getProductId())
                    .setDescription(item.getDescription())
                    .setCost(item.getCost())
                    .build())
                .toList())
        .build();

    kafkaTemplate.send("order_created_dev", orderEvent);
  }


}

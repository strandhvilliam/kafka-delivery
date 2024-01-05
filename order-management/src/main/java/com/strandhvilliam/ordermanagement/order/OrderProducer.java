package com.strandhvilliam.ordermanagement.order;

import com.strandhvilliam.events.proto.OrderEvent;
import com.strandhvilliam.events.proto.OrderEventItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

  @Autowired
  private KafkaTemplate<String, OrderEvent> kafkaTemplate;

  public void send(OrderEntity orderEntity) {
    var orderEvent = OrderEvent.newBuilder()
        .setId(orderEntity.getId())
        .setDate(orderEntity.getDate())
        .setStatus(orderEntity.getStatus())
        .setUserId(orderEntity.getUserId());

    if (orderEntity.getItems() != null) {
      orderEvent.addAllItems(orderEntity.getItems().stream().map(item -> OrderEventItem.newBuilder()
          .setId(item.getId())
          .setProductId(item.getProductId())
          .setDescription(item.getDescription())
          .setCost(item.getCost())
          .build()).toList());
    }
    kafkaTemplate.send("order_created_dev", orderEvent.build());
  }


}

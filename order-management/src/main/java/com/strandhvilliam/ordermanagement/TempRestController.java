package com.strandhvilliam.ordermanagement;

import com.strandhvilliam.ordermanagement.order.OrderProducer;
import com.strandhvilliam.ordermanagement.order.OrderEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempRestController {

  OrderProducer orderProducer;

  public TempRestController(OrderProducer orderProducer) {
    this.orderProducer = orderProducer;
  }

  @GetMapping("/send")
  public void sendOrder() {
    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setId("123");
    orderEntity.setUserId("123");
    orderEntity.setStatus("PROCESSING");
    orderEntity.setDate("2021-09-01");

    orderProducer.send(orderEntity);
  }
}

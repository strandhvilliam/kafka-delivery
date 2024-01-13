package com.strandhvilliam.customerapi.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class GeoLocEventConsumer {

  public final String GROUP_ID = "customer-api-group";

  private final Logger logger = LoggerFactory.getLogger(GeoLocEventConsumer.class.getSimpleName());


  @KafkaListener(
      topics = {"delivery_geolocation_dev"},
      groupId = GROUP_ID,
      containerFactory = "geoLocEventListenerContainerFactory"
  )
  public void consume(String event) {
    logger.info(String.format("##### %n Consumed geo location event -> %s %n #####", event));
  }
}

package com.strandhvilliam.geolocationhandler.producers;

import com.strandhvilliam.geolocevent.proto.GeoLocEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class GeoLocProducer {

  private final Logger log = LoggerFactory.getLogger(GeoLocProducer.class.getSimpleName());

  private final KafkaTemplate<String, GeoLocEvent> kafkaTemplate;

  public GeoLocProducer(KafkaTemplate<String, GeoLocEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void send(String topic, GeoLocEvent entity) {
    log.info("Sending geo location event to topic: {}, with key: {}", topic, entity.getOrderId());
    kafkaTemplate.send(topic , entity.getOrderId(), entity);
  }


}

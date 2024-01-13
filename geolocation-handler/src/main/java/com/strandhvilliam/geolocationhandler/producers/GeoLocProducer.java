package com.strandhvilliam.geolocationhandler.producers;

import com.strandhvilliam.geolocevent.proto.GeoLocEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class GeoLocProducer {

  private final KafkaTemplate<String, GeoLocEvent> kafkaTemplate;

  public GeoLocProducer(KafkaTemplate<String, GeoLocEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void send(String topic, GeoLocEvent entity) {
    kafkaTemplate.send(topic , entity.getOrderId(), entity);
  }


}

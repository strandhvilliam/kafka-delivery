package com.strandhvilliam.geolocationhandler.producers;

import com.strandhvilliam.geolocationhandler.utils.CustomLogger;
import com.strandhvilliam.geolocevent.proto.GeoLocEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class GeoLocProducer {

private final CustomLogger log;
  private final KafkaTemplate<String, GeoLocEvent> kafkaTemplate;

  public GeoLocProducer(CustomLogger logger, KafkaTemplate<String, GeoLocEvent> kafkaTemplate) {
    this.log = logger;
    this.kafkaTemplate = kafkaTemplate;
  }

  public void send(String topic, GeoLocEvent entity) {
    log.info(String.format("Sending geo location event to topic: %s, with key: %s", topic, entity.getOrderId()), GeoLocProducer.class.getSimpleName());
    kafkaTemplate.send(topic , entity.getOrderId(), entity);
  }


}

package com.strandhvilliam.driverapi.producers;

import com.strandhvilliam.logevent.proto.LogEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

  private final KafkaTemplate<String, LogEvent> kafkaTemplate;

  public KafkaProducer(KafkaTemplate<String, LogEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void send(String topic, LogEvent event) {
    kafkaTemplate.send(topic, event);
  }

}

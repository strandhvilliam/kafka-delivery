package com.strandhvilliam.driveravailability.producers;

import com.strandhvilliam.logevent.proto.LogEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogEventProducer {

  @Qualifier("logEventTemplate")
  private final KafkaTemplate<String, LogEvent> kafkaTemplate;

  public LogEventProducer(KafkaTemplate<String, LogEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void send(String topic, LogEvent event) {
    kafkaTemplate.send(topic, event);
  }

}

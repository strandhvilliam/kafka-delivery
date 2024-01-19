package com.strandhvilliam.loggingcenter.consumers;


import com.strandhvilliam.logevent.proto.LogEvent;
import com.strandhvilliam.loggingcenter.services.LoggingCenterService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

  private static final String TOPIC = "log_events_dev";
  private final Logger log = LoggerFactory.getLogger(KafkaConsumer.class.getSimpleName());

  private final LoggingCenterService loggingCenterService;

  public KafkaConsumer(LoggingCenterService loggingCenterService) {
    this.loggingCenterService = loggingCenterService;
  }

  @KafkaListener(topics = TOPIC, groupId = "logging-center-group", containerFactory = "eventListenerContainerFactory")
  public void consume(ConsumerRecord<String, LogEvent> event) {
    log.info("Consumed log from: {}", event.value().getSource());
    loggingCenterService.emitLogEvent(event.value());
  }
}

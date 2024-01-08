package com.strandhvilliam.driverapi.consumers;

import com.strandhvilliam.driverapi.services.DriverApiService;
import com.strandhvilliam.events.proto.JobEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.strandhvilliam.driverapi.config.JobConsumerConfig.GROUP_ID;
import static com.strandhvilliam.driverapi.config.JobConsumerConfig.TOPIC;

@Component
public class JobConsumer {
  private final Logger logger = LoggerFactory.getLogger(JobConsumer.class.getSimpleName());

  private final DriverApiService driverApiService;

  public JobConsumer(DriverApiService driverApiService) {
    this.driverApiService = driverApiService;
  }

  @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
  public void consume(ConsumerRecord<String, JobEvent> event) {
    logger.info(String.format("#### -> Consumed message -> %s", event.value()));
    driverApiService.sendEvent(event.value().getDriverId(), event.value().getOrderId());
  }
}

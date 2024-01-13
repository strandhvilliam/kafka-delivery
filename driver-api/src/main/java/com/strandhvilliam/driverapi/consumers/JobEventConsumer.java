package com.strandhvilliam.driverapi.consumers;

import com.strandhvilliam.driverapi.services.DriverApiService;
import com.strandhvilliam.jobevent.proto.JobEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.strandhvilliam.driverapi.config.KafkaConsumerConfig.GROUP_ID;

@Component
public class JobEventConsumer {
  private final Logger logger = LoggerFactory.getLogger(JobEventConsumer.class.getSimpleName());

  private final DriverApiService driverApiService;

  public JobEventConsumer(DriverApiService driverApiService) {
    this.driverApiService = driverApiService;
  }

  @KafkaListener(topics = "driver_assigned_dev", groupId = GROUP_ID, containerFactory = "jobEventListenerContainerFactory")
  public void consume(ConsumerRecord<String, JobEvent> event) {
    logger.info(String.format("#### -> Consumed job event -> %s", event.value()));
    driverApiService.sendEvent(event.value().getDriverId(), event.value().getOrderId(), "driver_assigned");
  }





}

package com.strandhvilliam.driverapi.consumers;

import com.strandhvilliam.driverapi.services.DriverApiService;
import com.strandhvilliam.driverapi.utils.CustomLogger;
import com.strandhvilliam.jobevent.proto.JobEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.strandhvilliam.driverapi.config.KafkaConsumerConfig.GROUP_ID;

@Component
public class JobEventConsumer {

  private final CustomLogger logger;

  private final DriverApiService driverApiService;

  public JobEventConsumer(CustomLogger logger, DriverApiService driverApiService) {
    this.logger = logger;
    this.driverApiService = driverApiService;
  }

  @KafkaListener(topics = "driver_assigned_dev", groupId = GROUP_ID, containerFactory = "jobEventListenerContainerFactory")
  public void consume(ConsumerRecord<String, JobEvent> event) {
    logger.info(String.format("#### -> Consumed job event -> %s", event.value().getOrderId()), JobEventConsumer.class.getSimpleName());
    driverApiService.emitDriverEvent(event.value());
  }





}

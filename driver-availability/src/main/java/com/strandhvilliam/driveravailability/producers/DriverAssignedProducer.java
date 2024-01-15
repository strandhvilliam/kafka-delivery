package com.strandhvilliam.driveravailability.producers;

import com.strandhvilliam.driveravailability.entities.JobEntity;
import com.strandhvilliam.jobevent.proto.Coordinates;
import com.strandhvilliam.jobevent.proto.JobEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class DriverAssignedProducer {
  private static final String TOPIC = "driver_assigned_dev";

  private final Logger logger = LoggerFactory.getLogger(DriverAssignedProducer.class.getSimpleName());
  private final KafkaTemplate<String, JobEvent> kafkaTemplate;

  public DriverAssignedProducer(KafkaTemplate<String, JobEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  /**
   * Sends a job event to the kafka topic.
   * No key is provided, so the message will be sent to a random partition as message order is not important.
   *
   * @param entity the job entity
   */
  public void send(JobEntity entity) {
    var jobEvent = JobEvent.newBuilder()
        .setId(entity.getId())
        .setCreatedAt(entity.getCreatedAt().toString())
        .setDriverId(entity.getDriver().getId())
        .setCompleted(entity.isCompleted())
        .setOrderId(entity.getOrderId())
        .setOrigin(Coordinates.newBuilder()
            .setLatitude(entity.getOrigin().getLatitude())
            .setLongitude(entity.getOrigin().getLongitude())
            .build())
        .setDestination(Coordinates.newBuilder()
            .setLatitude(entity.getDestination().getLatitude())
            .setLongitude(entity.getDestination().getLongitude())
            .build())
        .build();

    logger.info("Producing job event: {}", jobEvent.getId());
    kafkaTemplate.send(TOPIC, jobEvent);
  }




}

package com.strandhvilliam.driveravailability.producers;

import com.strandhvilliam.driveravailability.entities.JobEntity;
import com.strandhvilliam.events.proto.Coordinates;
import com.strandhvilliam.events.proto.JobEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class DriverAssignedProducer {
  private static final String TOPIC = "driver_assigned_dev";

  private final KafkaTemplate<String, JobEvent> kafkaTemplate;

  public DriverAssignedProducer(KafkaTemplate<String, JobEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void send(JobEntity entity) {
    var jobEvent = JobEvent.newBuilder()
        .setId(entity.getId())
        .setTimestamp(entity.getTimestamp().toString())
        .setDriverId(entity.getDriver().getId())
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

    kafkaTemplate.send(TOPIC, jobEvent);
  }




}

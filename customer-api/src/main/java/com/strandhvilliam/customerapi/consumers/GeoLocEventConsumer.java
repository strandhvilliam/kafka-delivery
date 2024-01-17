package com.strandhvilliam.customerapi.consumers;

import com.strandhvilliam.customerapi.services.CustomerApiService;
import com.strandhvilliam.geolocevent.proto.GeoLocEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class GeoLocEventConsumer {

  public final String GROUP_ID = "customer-api-group";

  private final Logger logger = LoggerFactory.getLogger(GeoLocEventConsumer.class.getSimpleName());

  private final CustomerApiService customerApiService;

  public GeoLocEventConsumer(CustomerApiService customerApiService) {
    this.customerApiService = customerApiService;
  }

  @KafkaListener(
      topics = {"delivery_geolocation_dev"},
      groupId = GROUP_ID,
      containerFactory = "geoLocEventListenerContainerFactory"
  )
  public void consume(ConsumerRecord<String, GeoLocEvent> event) {
    logger.info(String.format("##### %n Consumed geo location event -> %s %n #####", event));
    customerApiService.emitGeoLocEvent(event.value().getCustomerId(), event.value());
  }
}

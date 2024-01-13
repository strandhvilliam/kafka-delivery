package com.strandhvilliam.customerapi.config;


import com.strandhvilliam.geolocevent.proto.GeoLocEvent;
import com.strandhvilliam.orderevent.proto.OrderEvent;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

  public static final String GROUP_ID = "customer-api-group";

  @Bean()
  public ConsumerFactory<String, OrderEvent> orderEventConsumerFactory() {
    Map<String, Object> config = getDefaultConfig();
    config.put(KafkaProtobufDeserializerConfig.SPECIFIC_PROTOBUF_VALUE_TYPE, OrderEvent.class.getName());
    return new DefaultKafkaConsumerFactory<>(config);
  }

  @Bean()
  public ConcurrentKafkaListenerContainerFactory<String, OrderEvent> orderEventListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, OrderEvent> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(orderEventConsumerFactory());
    return factory;
  }

  @Bean()
  public ConsumerFactory<String, GeoLocEvent> geoLocEventConsumerFactory() {
    Map<String, Object> config = getDefaultConfig();
    config.put(KafkaProtobufDeserializerConfig.SPECIFIC_PROTOBUF_VALUE_TYPE, GeoLocEvent.class.getName());
    return new DefaultKafkaConsumerFactory<>(config);
  }

  @Bean()
  public ConcurrentKafkaListenerContainerFactory<String, GeoLocEvent> geoLocEventListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, GeoLocEvent> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(geoLocEventConsumerFactory());
    return factory;
  }

  private Map<String, Object> getDefaultConfig() {
    Map<String, Object> config = new HashMap<>();
    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    config.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
    config.put("schema.registry.url", "http://localhost:8081");
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        "org.apache.kafka.common.serialization.StringDeserializer");
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        "io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer");
    return config;
  }

}

package com.strandhvilliam.loggingcenter.config;

import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;
import com.strandhvilliam.logevent.proto.LogEvent;

@Configuration
public class KafkaConsumerConfig {

    public static final String GROUP_ID = "logging-center-group";

    @Bean()
    public ConsumerFactory<String, LogEvent> kafkaConsumerFactory() {
Map<String, Object> config = new HashMap<>();
    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    config.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
    config.put("schema.registry.url", "http://localhost:8081");
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        "org.apache.kafka.common.serialization.StringDeserializer");
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        "io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer");
      config.put(KafkaProtobufDeserializerConfig.SPECIFIC_PROTOBUF_VALUE_TYPE, LogEvent.class.getName());
      return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean()
    public ConcurrentKafkaListenerContainerFactory<String, LogEvent> eventListenerContainerFactory() {
      ConcurrentKafkaListenerContainerFactory<String, LogEvent> factory =
          new ConcurrentKafkaListenerContainerFactory<>();
      factory.setConsumerFactory(kafkaConsumerFactory());
      return factory;
    }
}

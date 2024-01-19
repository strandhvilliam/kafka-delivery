package com.strandhvilliam.productcatalog.utils;


import com.strandhvilliam.logevent.proto.LogEvent;
import com.strandhvilliam.productcatalog.producers.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomLogger {

  private Map<String, Logger> loggers = new ConcurrentHashMap<>();


  private final KafkaProducer kafkaProducer;

  public CustomLogger(KafkaProducer kafkaProducer) {
    this.kafkaProducer = kafkaProducer;
  }


  public void info(String message, String loggerName) {
    Logger logger = loggers.get(loggerName);
    if (logger == null) {
      logger = LoggerFactory.getLogger(loggerName);
      loggers.put(loggerName, logger);
    }
    logger.info(message);
    kafkaProducer.send("log_events_dev", buildLogEvent(message, loggerName));

  }

  private LogEvent buildLogEvent(String message, String loggerName) {
    return LogEvent.newBuilder()
        .setTimestamp(LocalDateTime.now().toString())
        .setLevel("INFO")
        .setSource(loggerName)
        .setMessage(message)
        .build();
  }
}

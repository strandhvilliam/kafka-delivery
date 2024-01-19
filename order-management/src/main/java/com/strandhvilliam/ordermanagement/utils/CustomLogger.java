package com.strandhvilliam.ordermanagement.utils;


import com.strandhvilliam.ordermanagement.producers.LogEventProducer;
import com.strandhvilliam.logevent.proto.LogEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomLogger {

  private final Map<String, Logger> loggers = new ConcurrentHashMap<>();
  private final LogEventProducer kafkaProducer;

  public CustomLogger(LogEventProducer kafkaProducer) {
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

package com.strandhvilliam.loggingcenter.services;


import com.strandhvilliam.logevent.proto.LogEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoggingCenterService {

  private Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

  public void emitLogEvent(LogEvent event) {
    emitters.forEach((id, emitter) -> {
      try {
        String json = "{\"timestamp\":\"" + event.getTimestamp() + "\",\"level\":\"" + event.getLevel() + "\",\"source\":\"" + event.getSource() + "\",\"message\":\"" + event.getMessage() + "\"}";
        emitter.send(json);
      } catch (Exception e) {
        emitters.remove(id);
      }
    });
  }

  public void addEmitter(SseEmitter emitter, String id) {
    emitters.put(id, emitter);
  }
}

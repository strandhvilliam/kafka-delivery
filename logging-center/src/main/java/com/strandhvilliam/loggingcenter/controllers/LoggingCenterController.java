package com.strandhvilliam.loggingcenter.controllers;

import com.strandhvilliam.loggingcenter.services.LoggingCenterService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class LoggingCenterController {

  private final LoggingCenterService loggingCenterService;

  public LoggingCenterController(LoggingCenterService loggingCenterService) {
    this.loggingCenterService = loggingCenterService;
  }

  @GetMapping("/subscribe")
  @CrossOrigin(origins = "*")
  public SseEmitter subscribe() {
    SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
    String id = java.util.UUID.randomUUID().toString();
    loggingCenterService.addEmitter(emitter, id);
    return emitter;
  }
}

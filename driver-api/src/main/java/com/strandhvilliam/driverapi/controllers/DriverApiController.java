package com.strandhvilliam.driverapi.controllers;

import com.strandhvilliam.driverapi.services.DriverApiService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
public class DriverApiController {

  private final DriverApiService driverApiService;

  public DriverApiController(DriverApiService driverApiService) {
    this.driverApiService = driverApiService;
  }

  @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  @CrossOrigin(origins = "*")
  public SseEmitter subscribe(@RequestParam String driverId) {
    var emitter = new SseEmitter(Long.MAX_VALUE);
    driverApiService.addEmitter(driverId, emitter);
    return emitter;
  }

  @GetMapping("/jobs")
  @CrossOrigin(origins = "*")
  public List<String> getJobs(@RequestParam String driverId) {
    return driverApiService.getJobs(driverId);
  }
}

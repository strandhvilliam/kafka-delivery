package com.strandhvilliam.driverapi.controllers;

import com.strandhvilliam.driverapi.dto.DriverLocationDto;
import com.strandhvilliam.driverapi.services.DriverApiService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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

  @PostMapping("/pickup/{orderId}")
  @CrossOrigin(origins = "*")
  public String pickupOrder(@PathVariable String orderId) {
    return driverApiService.pickupOrder(orderId);
  }

  @PostMapping("/deliver/{orderId}")
  @CrossOrigin(origins = "*")
  public String deliverOrder(@PathVariable String orderId) {
    return driverApiService.deliverOrder(orderId);
  }

  @GetMapping("/jobs")
  @CrossOrigin(origins = "*")
  public List<String> getJobs(@RequestParam String driverId) {
    return driverApiService.getJobs(driverId);
  }

  @PostMapping("/geolocation")
  @CrossOrigin(origins = "*")
  public void sendGeoLocation(@RequestBody @Valid DriverLocationDto dto) {
    driverApiService.sendGeoLocation(dto);
  }
}

package com.strandhvilliam.driverapi.services;

import com.strandhvilliam.driveravailability.grpc.DriverIdRequest;
import com.strandhvilliam.driveravailability.grpc.DriverServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DriverApiService {

  @GrpcClient("driver-availability")
  private DriverServiceGrpc.DriverServiceBlockingStub driverService;

  private final Logger logger = LoggerFactory.getLogger(DriverApiService.class.getSimpleName());

  private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

  public void addEmitter(String driverId, SseEmitter emitter) {
    emitters.put(driverId, emitter);
    emitter.onCompletion(() -> emitters.remove(driverId));
    emitter.onTimeout(() -> emitters.remove(driverId));
    logger.info("Added emitter for driver: " + driverId);
  }


  public List<String> getJobs(String driverId) {
    var response = driverService.getDriverById(
        DriverIdRequest.newBuilder()
            .setId(driverId)
            .build());
    return response.getJobsList().stream().map(job -> "New job assigned: " + job.getOrderId()).toList();
  }

  public void sendEvent(String driverId, String orderId) {
    var emitter = emitters.get(driverId);
    logger.info("Trying to send event to driver: " + driverId);
    logger.info("Trying to send event to emitter: " + emitter);
    if (emitter != null) {
      try {
        emitter.send("New job assigned: " + orderId);
        logger.info("Sent event to driver: " + driverId);
      } catch (Exception e) {
        emitters.remove(driverId);
      }
    }
  }

}

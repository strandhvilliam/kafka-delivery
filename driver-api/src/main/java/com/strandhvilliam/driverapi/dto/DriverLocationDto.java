package com.strandhvilliam.driverapi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
public class DriverLocationDto {
  @NotEmpty
  private String driverId;
  @NotEmpty
  private String orderId;
  @Min(-90)
  @Max(90)
  private double latitude;
  @Min(-180)
  @Max(180)
  private double longitude;
}

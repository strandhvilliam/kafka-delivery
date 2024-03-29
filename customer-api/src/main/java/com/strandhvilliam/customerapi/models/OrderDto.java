package com.strandhvilliam.customerapi.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto {
    @NotEmpty
    private String customerId;
    @NotEmpty
    private String restaurantId;
    @NotEmpty
    private List<String> productIds;

}

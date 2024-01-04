package com.strandhvilliam.customerapi.dtos;

import java.util.List;

public record OrderDto(String userId, List<String> products) {
}

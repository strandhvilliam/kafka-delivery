package com.strandhvilliam.customerapi.order;

import java.util.List;

public record OrderDto(String userId, List<String> products) {
}

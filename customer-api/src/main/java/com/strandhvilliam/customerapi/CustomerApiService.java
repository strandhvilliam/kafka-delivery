package com.strandhvilliam.customerapi;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.netflix.discovery.converters.Auto;
import com.strandhvilliam.customerapi.services.ProductClientService;
import com.strandhvilliam.productcatalog.grpc.ListProductsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strandhvilliam.customerapi.dtos.OrderDto;
import com.strandhvilliam.customerapi.models.Order;
import com.strandhvilliam.customerapi.models.Product;

@Service
public class CustomerApiService {

  @Autowired
  private ProductClientService productClientService;

  public Order createOrder(OrderDto dto) {
    var products = productClientService.getManyProducts(dto.products());
    String uuid = UUID.randomUUID().toString();
    LocalDateTime date = LocalDateTime.now();

    return new Order.Builder()
        .setId(uuid)
        .setDate(date)
        .setUserId(dto.userId())
        .addProducts(products)
        .build();
  }

}

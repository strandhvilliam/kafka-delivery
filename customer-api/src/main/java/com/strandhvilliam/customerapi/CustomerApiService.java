package com.strandhvilliam.customerapi;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.strandhvilliam.customerapi.dtos.OrderDto;
import com.strandhvilliam.customerapi.models.Order;
import com.strandhvilliam.customerapi.models.Product;

@Service
public class CustomerApiService {

  public Order createOrder(OrderDto dto) {

    // TODO: fetch products from product service

    List<Product> products = dto.products().stream().map(productId -> {
      return new Product.Builder()
          .setId(productId)
          .setDescription("Product " + productId)
          .setCost(Math.random() > 0.5 ? 100 : 200)
          .build();
    }).toList();

    String uuid = UUID.randomUUID().toString();
    LocalDateTime date = LocalDateTime.now();

    Order order = new Order.Builder()
        .setId(uuid)
        .setDate(date)
        .setUserId(dto.userId())
        .addProducts(products)
        .build();

    // TODO: send order as event to order service via kafka

    return order;
  }

}

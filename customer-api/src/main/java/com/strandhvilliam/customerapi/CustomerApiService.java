package com.strandhvilliam.customerapi;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strandhvilliam.customerapi.order.OrderDto;
import com.strandhvilliam.customerapi.order.Order;
import com.strandhvilliam.customerapi.product.ProductClientService;

@Service
public class CustomerApiService {

//  @Autowired
//  private ProductClientService productClientService;
//
//  public Order createOrder(OrderDto dto) {
//    var products = productClientService.getManyProducts(dto.products());
//    String uuid = UUID.randomUUID().toString();
//    LocalDateTime date = LocalDateTime.now();
//
//    return new Order.Builder()
//        .setId(uuid)
//        .setDate(date)
//        .setUserId(dto.userId())
//        .addProducts(products)
//        .build();
//  }

}

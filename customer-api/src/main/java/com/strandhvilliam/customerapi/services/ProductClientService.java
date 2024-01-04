package com.strandhvilliam.customerapi.services;

import com.strandhvilliam.customerapi.models.Product;
import com.strandhvilliam.productcatalog.grpc.*;
import org.springframework.stereotype.Service;
import net.devh.boot.grpc.client.inject.GrpcClient;

import java.util.List;

@Service
public class ProductClientService {

  @GrpcClient("product_catalog")
  ProductServiceGrpc.ProductServiceBlockingStub productServiceStub;

  public List<Product> getManyProducts(List<String> ids) {
    ManyProductIdRequest request = ManyProductIdRequest.newBuilder().addAllIds(ids).build();
    ListProductsResponse response = productServiceStub.getManyProducts(request);
    return response.getProductsList().stream().map(productResponse -> new Product.Builder()
        .setId(productResponse.getId())
        .setDescription(productResponse.getDescription())
        .setCost(productResponse.getCost())
        .build()).toList();
  }

}

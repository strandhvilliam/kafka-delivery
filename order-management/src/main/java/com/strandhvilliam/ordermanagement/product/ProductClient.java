package com.strandhvilliam.ordermanagement.product;

import com.strandhvilliam.productcatalog.grpc.*;
import org.springframework.stereotype.Service;
import net.devh.boot.grpc.client.inject.GrpcClient;

import java.util.List;

@Service
public class ProductClient {

  @GrpcClient("product_catalog")
  ProductServiceGrpc.ProductServiceBlockingStub productServiceStub;

  public List<ProductModel> getManyProducts(List<String> ids) {
    ManyProductIdRequest request = ManyProductIdRequest.newBuilder().addAllIds(ids).build();
    ListProductsResponse response = productServiceStub.getManyProducts(request);
    return response.getProductsList().stream().map(productResponse -> new ProductModel.Builder()
        .setId(productResponse.getId())
        .setDescription(productResponse.getDescription())
        .setCost(productResponse.getCost())
        .build())
        .toList();
  }

}

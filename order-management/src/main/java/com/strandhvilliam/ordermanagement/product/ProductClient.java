package com.strandhvilliam.ordermanagement.product;

import com.strandhvilliam.productcatalog.grpc.*;
import org.springframework.stereotype.Service;
import net.devh.boot.grpc.client.inject.GrpcClient;

import java.util.List;

@Service
public class ProductClient {

  @GrpcClient("product_catalog")
  private ProductServiceGrpc.ProductServiceBlockingStub productServiceStub;

  public ListProductsResponse getManyProducts(List<String> ids) {
    ManyProductIdRequest request = ManyProductIdRequest.newBuilder().addAllIds(ids).build();
    return productServiceStub.getManyProducts(request);
  }

}

package com.strandhvilliam.ordermanagement.services;

import com.strandhvilliam.productcatalog.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import net.devh.boot.grpc.client.inject.GrpcClient;

import java.util.List;

@Service
public class ProductClient {

  private final Logger log = LoggerFactory.getLogger(ProductClient.class.getSimpleName());

  private static final String PRODUCT_CATALOG_CLIENT = "product-catalog";
  @GrpcClient(PRODUCT_CATALOG_CLIENT)
  private ProductServiceGrpc.ProductServiceBlockingStub productServiceStub;

  public ListProductsResponse getManyProducts(List<String> ids) {
    log.info("getManyProducts" + ids);
    ManyProductIdRequest request = ManyProductIdRequest.newBuilder().addAllIds(ids).build();
    return productServiceStub.getManyProducts(request);
  }

}

package com.strandhvilliam.productcatalog.services;

import com.strandhvilliam.productcatalog.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import org.springframework.beans.factory.annotation.Autowired;

import com.strandhvilliam.productcatalog.entities.ProductEntity;
import com.strandhvilliam.productcatalog.repository.ProductRepository;

@GrpcService
public class ProductCatalogService extends
    ProductServiceGrpc.ProductServiceImplBase {

  private final ProductRepository productRepository;

  public ProductCatalogService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public void getProduct(
      ProductIdRequest req,
      StreamObserver<ProductResponse> responseObserver) {

    productRepository.findById(req.getId())
        .ifPresentOrElse(
            product -> responseObserver.onNext(buildProductResponse(product)),
            responseObserver::onCompleted);
  }

  @Override
  public void getManyProducts(ManyProductIdRequest req, StreamObserver<ListProductsResponse> responseObserver) {
    System.out.println("getManyProducts" + req.getIdsList());
    ListProductsResponse.Builder responseBuilder = ListProductsResponse.newBuilder();
    productRepository.findAll().stream().filter(product -> req.getIdsList().contains(product.getId()))
        .forEach(product ->
            responseBuilder.addProducts(buildProductResponse(product)));
    responseObserver.onNext(responseBuilder.build());
    responseObserver.onCompleted();
  }

  @Override
  public void createProduct(
      CreateProductRequest req,
      StreamObserver<ProductResponse> responseObserver) {
    ProductEntity product = new ProductEntity();
    product.setDescription(req.getDescription());
    product.setCost(req.getCost());
    productRepository.save(product);
    responseObserver.onNext(buildProductResponse(product));
    responseObserver.onCompleted();
  }

  private ProductResponse buildProductResponse(ProductEntity product) {
    return ProductResponse.newBuilder()
        .setId(product.getId())
        .setDescription(product.getDescription())
        .setRestaurantId(product.getRestaurantId())
        .setCost(product.getCost())
        .build();
  }

}

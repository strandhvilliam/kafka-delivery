package com.strandhvilliam.productcatalog.services;

import com.strandhvilliam.productcatalog.grpc.*;
import com.strandhvilliam.productcatalog.utils.CustomLogger;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import org.springframework.beans.factory.annotation.Autowired;

import com.strandhvilliam.productcatalog.entities.ProductEntity;
import com.strandhvilliam.productcatalog.repository.ProductRepository;

@GrpcService
public class ProductCatalogService extends
    ProductServiceGrpc.ProductServiceImplBase {

  private final ProductRepository productRepository;

  private final CustomLogger logger;

  public ProductCatalogService(ProductRepository productRepository, CustomLogger logger) {
    this.productRepository = productRepository;
    this.logger = logger;
  }

  @Override
  public void getProduct(
      ProductIdRequest req,
      StreamObserver<ProductResponse> responseObserver) {

    logger.info("Getting product" + req.getId(), ProductCatalogService.class.getSimpleName());

    productRepository.findById(req.getId())
        .ifPresentOrElse(
            product -> responseObserver.onNext(buildProductResponse(product)),
            responseObserver::onCompleted);
  }

  @Override
  public void getManyProducts(ManyProductIdRequest req, StreamObserver<ListProductsResponse> responseObserver) {
    logger.info("Getting many products" + req.getIdsList(), ProductCatalogService.class.getSimpleName());
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
    logger.info("Creating product" + req.getDescription(), ProductCatalogService.class.getSimpleName());
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

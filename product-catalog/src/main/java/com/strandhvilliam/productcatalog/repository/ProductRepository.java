package com.strandhvilliam.productcatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strandhvilliam.productcatalog.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {

}

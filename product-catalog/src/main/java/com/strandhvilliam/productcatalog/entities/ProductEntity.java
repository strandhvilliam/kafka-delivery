package com.strandhvilliam.productcatalog.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class ProductEntity {

  @Id()
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "description")
  private String description;

  @Column(name = "restaurant_id")
  private String restaurantId;

  @Column(name = "cost")
  private double cost;


  public ProductEntity(String description, double cost) {
    this.description = description;
    this.cost = cost;
  }

}

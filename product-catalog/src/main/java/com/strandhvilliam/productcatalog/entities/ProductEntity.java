package com.strandhvilliam.productcatalog.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "products")
public class ProductEntity {

  @Id()
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "description")
  private String desciption;

  @Column(name = "cost")
  private double cost;

  public ProductEntity() {
  }

  public ProductEntity(String description, double cost) {
    this.desciption = description;
    this.cost = cost;
  }

  public ProductEntity(String id, String description, double cost) {
    this.id = id;
    this.desciption = description;
    this.cost = cost;
  }

  public String getId() {
    return id;
  }

  public String getDescription() {
    return desciption;
  }

  public double getCost() {
    return cost;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setDescription(String description) {
    this.desciption = description;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

}

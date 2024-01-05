package com.strandhvilliam.ordermanagement.product;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProductModel {
  protected final String id;
  protected final String description;
  protected final double cost;

  private ProductModel(Builder builder) {
    this.id = builder.id;
    this.description = builder.description;
    this.cost = builder.cost;
  }

  public static class Builder {
    private String id;
    private String description;
    private double cost;

    public Builder setId(String id) {
      this.id = id;
      return this;
    }

    public Builder setDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder setCost(double cost) {
      this.cost = cost;
      return this;
    }

    public ProductModel build() {
      return new ProductModel(this);
    }
  }

}

package com.strandhvilliam.customerapi.models;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Product {
  protected final String id;
  protected final String description;
  protected final int cost;

  private Product(Builder builder) {
    this.id = builder.id;
    this.description = builder.description;
    this.cost = builder.cost;
  }

  public static class Builder {
    private String id;
    private String description;
    private int cost;

    public Builder setId(String id) {
      this.id = id;
      return this;
    }

    public Builder setDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder setCost(int cost) {
      this.cost = cost;
      return this;
    }

    public Product build() {
      return new Product(this);
    }
  }

}

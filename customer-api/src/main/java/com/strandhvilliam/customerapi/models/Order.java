package com.strandhvilliam.customerapi.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString()
public class Order {
  protected final String id;
  protected final LocalDateTime date;
  protected final List<Product> products;
  protected final String userId;

  private Order(Builder builder) {
    this.id = builder.id;
    this.date = builder.date;
    this.userId = builder.userId;
    this.products = builder.products;
  }

  public static class Builder {
    private String id;
    private LocalDateTime date;
    private String userId;
    private List<Product> products = new ArrayList<>();

    public Builder setId(String id) {
      this.id = id;
      return this;
    }

    public Builder setDate(LocalDateTime date) {
      this.date = date;
      return this;
    }

    public Builder setUserId(String userId) {
      this.userId = userId;
      return this;
    }

    public Builder addProducts(List<Product> items) {
      this.products.addAll(items);
      return this;
    }

    public Order build() {
      return new Order(this);
    }
  }

}

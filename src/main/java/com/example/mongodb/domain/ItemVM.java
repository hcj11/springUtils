package com.example.mongodb.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by hcj on 18-8-3
 */
@Setter
@Getter
public class ItemVM {
  // 商品信息
  private String id;
  private String sku;
  private String name;
  private Double quantity;
  private Price price;

  public ItemVM() {
  }

  public ItemVM(String id, String sku, String name, Double quantity,
      Price price) {
    this.id = id;
    this.sku = sku;
    this.name = name;
    this.quantity = quantity;
    this.price = price;
  }
}

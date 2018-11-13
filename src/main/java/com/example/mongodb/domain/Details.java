package com.example.mongodb.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by hcj on 18-8-3
 */
@Setter
@Getter
@ToString
public class Details {
  private Double weight;
  private String weight_units;
  private Double model_num;
  // 制造商
  private String manufacture;
  private String color;

  public Details(Double weight, String weight_units, Double model_num, String manufacture,
      String color) {
    this.weight = weight;
    this.weight_units = weight_units;
    this.model_num = model_num;
    this.manufacture = manufacture;
    this.color = color;
  }
}

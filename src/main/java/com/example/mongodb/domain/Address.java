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
public class Address {
  private String name;
  private String street;
  private String city;  //
  private String province; //
  private Double zip; // 邮编

  public Address(String name, String street, String city, String province, Double zip) {
    this.name = name;
    this.street = street;
    this.city = city;
    this.province = province;
    this.zip = zip;
  }

  @Override
  public boolean equals(Object obj) {
    System.out.println(obj);
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    int i = super.hashCode();
    System.out.println(i);
    return i;
  }
}

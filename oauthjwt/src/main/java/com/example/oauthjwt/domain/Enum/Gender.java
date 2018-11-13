package com.example.oauthjwt.domain.Enum;

/**
 * Created by hcj on 18-8-11
 */
public enum  Gender {

  FEMALE("女"),MALE("男");
  private String value;

  Gender(String value) {
    this.value=value;
  }

  public String getValue() {
    return value;
  }
}

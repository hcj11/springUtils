package com.example.oauthjwt.resource;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by hcj on 18-8-10
 */
@Setter
@Getter
@ToString
public class SuccessVm<T> {
  private int code=0;
  private String message;
  private boolean success=true;
  private Integer total;
  private T data;

  public SuccessVm(String message, Integer total, T data) {
    this.message = message;
    this.total = total;
    this.data = data;
  }
}

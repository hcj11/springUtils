package com.example.oauthjwt.resource;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by hcj on 18-8-10
 */
@Setter
@Getter
@ToString
public class ErrorVm<T> {
  private int code=0;
  private String message;
  private boolean success=false;
  private Integer total;
  private T data;

  public ErrorVm(String message, Integer total, T data) {
    this.message = message;
    this.total = total;
    this.data = data;
  }
}

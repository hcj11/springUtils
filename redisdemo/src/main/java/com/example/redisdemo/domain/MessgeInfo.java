package com.example.redisdemo.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by hcj on 18-7-25
 */
@Setter
@Getter
@ToString
public class MessgeInfo implements Serializable {
  public static final long serialVersionUID = 1L;
  private Long id;
  private String message;

}

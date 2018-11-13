package com.example.oauthjwt.resource.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by hcj on 18-8-11
 */
@Setter
@Getter
@ToString
public class PageVM {
  private Integer page = 1;
  private Integer pageSize=50;
}

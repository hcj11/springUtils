package com.example.oauth2.Resource.vm;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Created by hcj on 18-7-26
 */
@Getter
@Setter
@Component
public class PageUtil {
  private long pageSize =10;
  // 当前页
  private long page=1;
  public PageUtil(){}
  public PageUtil(long pageSize, long page) {
    this.pageSize = pageSize;
    this.page = page;
  }
}

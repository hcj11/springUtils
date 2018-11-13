package com.example.redisdemo.config;

import com.example.redisdemo.Test.LockDemo1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

/**
 * Created by hcj on 18-7-27
 */
@Component
public class servletRequestHandledEventListener implements ApplicationListener<ServletRequestHandledEvent>{
  @Autowired
  LockDemo1 lockDemo1;


  @Override
  public void onApplicationEvent(ServletRequestHandledEvent event) {
    // 当并发上来时， 就会需要锁处理，
      System.out.println("总计： "+lockDemo1.getCount());
  }
}

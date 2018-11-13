package com.example.oauthjwt;

import org.springframework.security.config.annotation.ObjectPostProcessor;

/**
 * Created by hcj on 18-7-24
 */
public class CustomeObjectPostProcessor implements ObjectPostProcessor {

  @Override
  public Object postProcess(Object object) {
    return object;
  }
}

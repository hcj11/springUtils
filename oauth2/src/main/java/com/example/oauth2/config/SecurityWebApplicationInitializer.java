package com.example.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Created by hcj on 18-7-29
 */
@Configuration
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

  @Override
  protected boolean enableHttpSessionEventPublisher() {
    return true;
  }
}

package com.example.oauthjwt.config;

import javax.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfig {
  @Bean
  public FilterRegistrationBean someFilterRegistration() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(SecurityFilterConfig());
    registration.addUrlPatterns("/oauth/token");
    registration.setName("SecurityFilter");
    registration.setOrder(0);
    return registration;
  }

  /**
   * 创建一个bean
   * @return
   */
  @Bean(name = "SecurityFilterConfig")
  public Filter SecurityFilterConfig() {
    return new SecurityFilterConfig();
  }
}
package com.example.oauthjwt;

import com.example.oauthjwt.vm.ErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * Created by hcj on 18-7-23
 */
@Configuration
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {
  @Autowired
  ErrorHandler errorHandler;
  @Override
  public void configure(HttpSecurity http) throws Exception {
    // 定义用户资源访问的过期时间，
    http.csrf()
        .disable()
        .authorizeRequests().
        antMatchers("/get").
        access("hasAnyRole('ADMIN')")
        .antMatchers("/put").access("hasRole('USER')")
        .antMatchers("/login").permitAll()
        .antMatchers("/logout").permitAll()
        .antMatchers("/**").permitAll()
    .and().rememberMe().key("hcj").tokenValiditySeconds(10000);

  }
}

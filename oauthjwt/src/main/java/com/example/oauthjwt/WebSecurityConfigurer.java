package com.example.oauthjwt;

import com.example.oauthjwt.resource.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * EnableWebSecurity 关键啊. Created by hcj on 18-7-23
 */
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
  // authenticationManagerBean 来处理验证.

  @Autowired
  UserDetailsService userDetailsService;

  @Bean
  public BCryptPasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManager() throws Exception {
    ObjectPostProcessor customeObjectPostProcessor = new CustomeObjectPostProcessor();
    AuthenticationManagerBuilder authenticationBuilder = new AuthenticationManagerBuilder(
        customeObjectPostProcessor);
    MyAuthenticationProvider myAuthenticationProvider = new MyAuthenticationProvider();
    myAuthenticationProvider.setUserDetailsService(userDetailsService);
    myAuthenticationProvider.setPasswordEncoder(encoder());
    authenticationBuilder.authenticationProvider(myAuthenticationProvider);

    return authenticationBuilder.build();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    System.out.println("HttpSecurity http ");
//    http.formLogin().
//    super.configure(http);
//    http.oauth2Login().
  }

  @Bean
  public UserDetailsService userDetailsService(BCryptPasswordEncoder passwordEncoder) {
    return new UserServiceImpl(passwordEncoder);
  }

}

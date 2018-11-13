package com.example.oauth2.Security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * Created by hcj on 18-7-24
 */
public class PreAuthenticationProvider extends PreAuthenticatedAuthenticationProvider {
//  @Autowired
//  UserDetailsService userDetailsService;
//  public MyAuthenticationProvider(
//      UserDetailsService userDetailsService) {
//    this.userDetailsService=userDetailsService;
//  }
  // 要不就implement AuthenticationProvider 自己提供校验规则.
  // 否则就 extends DaoAuthenticationProvider  让父类的方法进行提供,




}

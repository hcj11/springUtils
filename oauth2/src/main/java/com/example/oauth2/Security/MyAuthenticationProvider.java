package com.example.oauth2.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * Created by hcj on 18-7-24
 */
public class MyAuthenticationProvider extends DaoAuthenticationProvider {
//  @Autowired
//  UserDetailsService userDetailsService;
//  public MyAuthenticationProvider(
//      UserDetailsService userDetailsService) {
//    this.userDetailsService=userDetailsService;
//  }
  // 要不就implement AuthenticationProvider 自己提供校验规则.
  // 否则就 extends DaoAuthenticationProvider  让父类的方法进行提供,

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String s = authentication.getPrincipal().toString();
    System.out.println(s);
    return super.authenticate(authentication);
  }

//  @Override
//  public boolean supports(Class<?> authentication) {
//    return (UsernamePasswordAuthenticationToken.class
//        .isAssignableFrom(authentication) || PreAuthenticatedAuthenticationToken.class
//        .isAssignableFrom(authentication));
//
//  }

}

package com.example.oauthjwt;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Created by hcj on 18-7-24
 */
public class MyAuthenticationProvider extends DaoAuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String s = authentication.getPrincipal().toString();
    System.out.println(s);
    return super.authenticate(authentication);
  }
}

package com.example.oauth2.service;

import com.example.oauth2.domain.Role;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by hcj on 18-7-28
 */
public class AuthenticationUserDetailsServiceImpl implements AuthenticationUserDetailsService,Serializable {
  public  static final long serialVersionUID = 42L;

  transient RoleService roleService;
  transient UserService userService;

  public AuthenticationUserDetailsServiceImpl(UserService userService, RoleService roleService) {
    this.roleService=roleService;
    this.userService=userService;
  }


  @Override
  public UserDetails loadUserDetails(Authentication token) throws UsernameNotFoundException {

    UsernamePasswordAuthenticationToken principal = (UsernamePasswordAuthenticationToken) token.getPrincipal();
    String name = principal.getName();
    com.example.oauth2.domain.User byName = userService.findByName(name);

    if (byName == null) {
      throw new RuntimeException("未找到该用户");
    }
    Role one = roleService.findOne(byName.getRoleId());

    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new GrantedAuthority() {
      @Override
      public String getAuthority() {
        return one.getRolename();
      }
    });

    return new User(name, byName.getPassword(), authorities);
  }
}

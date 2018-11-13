package com.example.demo.security;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import java.io.Serializable;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Created by hcj on 18-7-22
 */

public class CustomUserDetailsService implements UserDetailsService ,Serializable{
  public CustomUserDetailsService(){}
  private UserService userService;
  private RoleService roleService;


  public CustomUserDetailsService(UserService userService,
      RoleService roleService) {
    this.roleService=roleService;
    this.userService=userService;
  }
//  public CustomUserDetailsService(UserService userService,RoleService roleService){
//
//  }

  // 绑定用户 和权限以及
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User byName = userService.findByName(username);

    if (byName != null) {
      // 密码不管?
//      userService.getPassword(byName.getPassword(),)

      Role one = roleService.findOne(byName.getRoleId());
      ArrayList<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new GrantedAuthority() {
        @Override
        public String getAuthority() {
          return one.getRolename();
        }
      });

      // 根据 username 查询 role
      org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
          byName.getLogin(), byName.getPassword(), true, true, true, true, authorities
      );
      return user;
    }
   throw new RuntimeException("用户不存在");
  }
}
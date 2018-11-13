package com.example.oauth2.service;

import com.example.oauth2.domain.Role;
import java.io.Serializable;
import java.util.ArrayList;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by hcj on 18-7-24
 */
public class UserServiceImpl implements UserDetailsService,Serializable {
  // 不要进行序列化
  private transient UserService userService;
  private transient  RoleService roleService;

  public UserServiceImpl(
      UserService userService, RoleService roleService) {
    this.userService = userService;
    this.roleService = roleService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    com.example.oauth2.domain.User byName = userService.findByName(username);

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

    return new User(username, byName.getPassword(), authorities);
  }

}

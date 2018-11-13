package com.example.oauthjwt.resource;

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
public class UserServiceImpl implements UserDetailsService {

  private final BCryptPasswordEncoder encoder;

  public UserServiceImpl(BCryptPasswordEncoder encoder) {
    this.encoder = encoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    String encode = this.encoder.encode("123");
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new GrantedAuthority() {
      @Override
      public String getAuthority() {
        return "ROLE_ADMIN";
      }
    });
    return new User(username, encode, authorities);
  }

  public static void main(String[] args) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    String encode = bCryptPasswordEncoder.encode("123");
    System.out.println(encode);
  }
}

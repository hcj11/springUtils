package com.example.oauth2.service;

import com.example.oauth2.Resource.vm.TokenDetailsVM;
import com.example.oauth2.Security.WebSecurityConfigurer;
import com.example.oauth2.domain.User;
import com.example.oauth2.redis.redistemplate.StringUtil;
import com.example.oauth2.repository.UserRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hcj on 18-7-22
 */
@Component
public class UserService {

  @Autowired
  BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired
  UserRepository userRepository;
  @Autowired
  RedisTemplate redisTemplate;
  @Autowired
  StringUtil stringUtil;

//  @Autowired
//  WebSecurityConfigurer webSecurityConfigurer;

  public boolean getPassword(String password,String oldPassword){
   return bCryptPasswordEncoder.matches(password,oldPassword);
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
  public User save(User user) {
    if(user.getPassword().length()!=60){
      String encode = bCryptPasswordEncoder.encode(user.getPassword());
      user.setPassword(encode);
    }

    user.setCreatedBy(user.getLogin());

     return userRepository.save(user);
  }

  // 只读事务, 在事务读取期间不会数据前后误差问题
  // 优化orm ,无锁,  多用于统计结果的处理
  @Transactional(readOnly = true)
  public List<User> findUsers() {
    return userRepository.findAll();
  }
  // 只读事务, 在事务读取期间不会数据前后误差问题
  // 优化orm ,无锁,  多用于统计结果的处理
  @Transactional(readOnly = true)
  public User findOne(Long id) {
    return userRepository.findById(id).orElse(null);
  }
  @Transactional(readOnly = true)
  public User findByName(String name) {
    // name 唯一性
    return userRepository.findByLogin(name).orElse(null);
  }

  public User findByAccessToken(String accessToken) {
    return userRepository.findByAccessToken(accessToken).orElse(null);
  }
//  @Autowired
//  private AuthenticationManager authenticationManager;

//  public void updateAuthrozition(TokenDetailsVM tokenDetailsVM) {
//    String authority = tokenDetailsVM.getAuthority();
//    DefaultTokenServices defaultTokenServices = webSecurityConfigurer.redisServerTokenServices();
//
//    OAuth2Authentication oAuth2Authentication = stringUtil.updateAuthrozition(tokenDetailsVM);
//    // 连待权限也改变？
//    OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
//    TokenRequest tokenRequest = new TokenRequest(oAuth2Request.getRequestParameters(),
//        oAuth2Request.getClientId(), oAuth2Request.getScope(), oAuth2Request.getGrantType());
//
//    //  json 解析？ refrsh token 重新解析权限
//    // String refreshTokenValue,  tokenRequest
//
//    defaultTokenServices.setAuthenticationManager(authenticationManager);
//
//    OAuth2AccessToken oAuth2AccessToken = defaultTokenServices
//        .refreshAccessToken(tokenDetailsVM.getRefreshToken(), tokenRequest);
//    System.out.println(oAuth2AccessToken);
//
//  }
}

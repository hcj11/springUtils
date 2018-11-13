package com.example.oauth2.Security;

import com.example.oauth2.serialization.CustomSerializationStrategy;
import com.example.oauth2.service.AuthenticationUserDetailsServiceImpl;
import com.example.oauth2.service.RoleService;
import com.example.oauth2.service.UserService;
import com.example.oauth2.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

/**
 * EnableWebSecurity 关键啊. Created by hcj on 18-7-23
 */
//@Order(1)
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
  // authenticationManagerBean 来处理验证.
  @Autowired
  UserDetailsService userDetailsService;
  @Autowired
  AuthenticationUserDetailsService authenticationUserDetailsService;

  @Autowired
  RedisConnectionFactory redisConnectionFactory;

  @Primary
  @Bean
  public DefaultTokenServices redisServerTokenServices(){
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore());
    defaultTokenServices.setSupportRefreshToken(true);

    return defaultTokenServices;
  }

  @Bean
  public TokenStore tokenStore(){
    RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
    CustomSerializationStrategy customSerializationStrategy = new CustomSerializationStrategy();
    redisTokenStore.setSerializationStrategy(customSerializationStrategy);
    return redisTokenStore;
  }

  @Bean
  public CustomtokenEnhancer tokenEnhancer(){
    return new CustomtokenEnhancer();
  }


  @Bean
  public BCryptPasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  @Bean //
  public AuthenticationManager authenticationManager() throws Exception {
    ObjectPostProcessor customeObjectPostProcessor = new CustomeObjectPostProcessor();
    AuthenticationManagerBuilder authenticationBuilder = new AuthenticationManagerBuilder(
        customeObjectPostProcessor);
    MyAuthenticationProvider myAuthenticationProvider = new MyAuthenticationProvider();
    myAuthenticationProvider.setUserDetailsService(userDetailsService);
    myAuthenticationProvider.setPasswordEncoder(encoder());
    authenticationBuilder.authenticationProvider(myAuthenticationProvider);
    // 预权限 preAuthenticatedUserDetailsService

    PreAuthenticatedAuthenticationProvider preProvider = new PreAuthenticatedAuthenticationProvider();
    preProvider.setPreAuthenticatedUserDetailsService(authenticationUserDetailsService);
    authenticationBuilder.authenticationProvider(preProvider);

    return authenticationBuilder.build();
  }

  @Bean
  public UserDetailsService userDetailsService(BCryptPasswordEncoder passwordEncoder,UserService userService,RoleService roleService) {
    return new UserServiceImpl(userService,roleService);
  }

  @Bean
  public AuthenticationUserDetailsService authenticationUserDetailsService(UserService userService,RoleService roleService) {

    return new AuthenticationUserDetailsServiceImpl(userService,roleService);
  }



// 内存存储。
  //  @Bean
//  public DefaultTokenServices serverTokenServices(){
//    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//    defaultTokenServices.setTokenStore(tokenStore());
//    defaultTokenServices.setSupportRefreshToken(true);
//    return defaultTokenServices;
//  }

//  @Bean
//  public TokenStore tokenStore(){
//    return new InMemoryTokenStore();
//  }
//
//  @Primary
}

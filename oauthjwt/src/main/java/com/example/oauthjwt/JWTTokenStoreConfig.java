package com.example.oauthjwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Created by hcj on 18-7-24
 */
@Configuration
public class JWTTokenStoreConfig  {
  @Autowired
  ServiceConfig serviceConfig;

  @Bean
  public TokenStore tokenStore(){
    return new JwtTokenStore(jwtAccessTokenConverter());
  }

  @Bean
  @Primary
  public DefaultTokenServices tokenServices(){
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore());
    // 设置支持刷新
    defaultTokenServices.setSupportRefreshToken(true);
    return defaultTokenServices;
  }
  @Bean
  public TokenEnhancer tokenEnhancer(){
    return new JWTTokenEnhancer();
  }
  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter(){
    JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
    // 设置定义用于签署令牌的签名秘钥
    jwtAccessTokenConverter.setSigningKey(serviceConfig.getSignkey());
    return jwtAccessTokenConverter;
  }





}

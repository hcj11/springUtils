package com.example.oauthjwt;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * 定义那些应用程序可以使用该服务 Created by hcj on 18-7-23
 * jwt 定义验证的标准结构，
 */
@Configuration
public class JWTOauth2Config extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private TokenStore tokenStore;

  @Autowired(required = false)
  private JwtAccessTokenConverter jwtAccessTokenConverter;
  @Autowired(required = false)
  private  JWTTokenEnhancer jwtTokenEnhancer;


  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    // 使用默认的校验器, 和 用户详细信息服务.
    TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtTokenEnhancer,jwtAccessTokenConverter,jwtTokenEnhancer));
    endpoints.tokenStore(tokenStore)
        .accessTokenConverter(jwtAccessTokenConverter)
        .tokenEnhancer(tokenEnhancerChain) // 增强器。
        .authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService);
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    // 定义客户端id 验证服务。第三方验证服务，
    clients.inMemory().
        withClient("eagleeye")
        .secret("$2a$10$ZG4Bo/ya4RaVbrzfwuEfVus/RiIbF4mZhaAAy24S6ubycCYyW0SAK").
        authorizedGrantTypes( "refresh_token","password", "client_credentials")
        .scopes("webclient").authorities("USER_ADMIN")
        .additionalInformation("haha").refreshTokenValiditySeconds(10000)
        .accessTokenValiditySeconds(30000).redirectUris("/auth/user")
        .and()
        .withClient("eagleey1")
        .secret("$2a$10$ZG4Bo/ya4RaVbrzfwuEfVus/RiIbF4mZhaAAy24S6ubycCYyW0SAK").
        authorizedGrantTypes( "refresh_token","password", "client_credentials")
        .scopes("mobileclient").authorities("USER_USER")
        .additionalInformation("haha").accessTokenValiditySeconds(10000)
        .refreshTokenValiditySeconds(30000).redirectUris("/auth/user");
  }


}

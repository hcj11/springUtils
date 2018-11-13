package com.example.oauth2.Security;

import java.util.Arrays;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * 定义那些应用程序可以使用该服务 Created by hcj on 18-7-23
 */
@Configuration
public class Oauth2Config extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private TokenStore tokenStore;
  @Autowired
  CustomtokenEnhancer customtokenEnhancer;


  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    // 定义客户端id 验证服务。第三方验证服务，
    // 定时刷新 还是, 即时刷新， 其实定时刷新即可， "refresh_token",
    clients.inMemory().
        withClient("eagleeye")
        .secret("$2a$10$ZG4Bo/ya4RaVbrzfwuEfVus/RiIbF4mZhaAAy24S6ubycCYyW0SAK").
         authorizedGrantTypes( "refresh_token", "password", "client_credentials")
        .scopes("webclient").authorities("HELLO")
        .additionalInformation("haha1").redirectUris("/auth/user")
        .and()
        .withClient("eagleeye1")
        .secret("$2a$10$ZG4Bo/ya4RaVbrzfwuEfVus/RiIbF4mZhaAAy24S6ubycCYyW0SAK").
        authorizedGrantTypes( "refresh_token","password", "client_credentials")
        .scopes("mobileclient").authorities("WORLD")
        .additionalInformation("haha2").redirectUris("/auth/user");
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    // 使用默认的校验器, 和 用户详细信息服务. tokenEnhancer(tokenEnhancer)?
    TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    tokenEnhancerChain.setTokenEnhancers(Collections.singletonList(customtokenEnhancer));
    endpoints.tokenStore(tokenStore)
        .tokenEnhancer(tokenEnhancerChain)
        .authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService);
  }
}

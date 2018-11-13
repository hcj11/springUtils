package com.example.oauthjwt;

import java.time.Instant;
import java.util.HashMap;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;


/**
 * 自定义增强器 Created by hcj on 18-7-24
 */
@Configuration
public class JWTTokenEnhancer extends JwtAccessTokenConverter implements TokenEnhancer {

  // 多个增强器，叠加使用，
  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
      OAuth2Authentication authentication) {
    // 增加操作 , 返回额外参数。
    HashMap<String, Object> map = new HashMap<>();
    map.put("username", authentication.getPrincipal());
//    map.put("registerTime", Instant.now().toEpochMilli());
//    map.put("refreshToken", accessToken.getRefreshToken().getValue());
//    // 存储第二次生成的key
//    if (accessToken.getRefreshToken().getValue().length() > 40) {
//      // 存储到数据库
//    }
    DefaultOAuth2AccessToken accessToken1 = (DefaultOAuth2AccessToken) accessToken;
    accessToken1.setAdditionalInformation(map);
    return accessToken1;
  }
}

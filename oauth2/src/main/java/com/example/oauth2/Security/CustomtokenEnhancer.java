package com.example.oauth2.Security;

import com.example.oauth2.domain.User;
import com.example.oauth2.service.UserService;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * Created by hcj on 18-7-26
 */
@Configuration
public class CustomtokenEnhancer implements TokenEnhancer {

  @Autowired
  transient UserService service;

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
      OAuth2Authentication authentication) {

    DefaultOAuth2AccessToken accessToken1 = (DefaultOAuth2AccessToken) accessToken;
    HashMap<String, Object> map = new HashMap<>();
    map.put("origination", "001");
    map.put("registerTime", Instant.now().toEpochMilli());

    org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
    User byName = service.findByName(principal.getUsername());
    byName.setAccessToken(accessToken1.getValue());
    byName.setRefreshToken(accessToken1.getRefreshToken().getValue());
    // 或者处于未激活状态.
    service.save(byName);

    // 当昵称，
    map.put("firstname",byName.getFirstName());
    accessToken1.setAdditionalInformation(map);
    return accessToken1;
  }
}

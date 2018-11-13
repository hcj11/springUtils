package com.example;

import com.example.oauthjwt.JWTTokenEnhancer;
import com.example.oauthjwt.ServiceConfig;
import com.example.oauthjwt.vm.Constants;
import com.example.oauthjwt.vm.TokenVM;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@RestController
@EnableAuthorizationServer
@EnableResourceServer
public class OauthjwtApplication {

  @Autowired
  ServiceConfig servletConfig;
  @Autowired
  DefaultTokenServices defaultTokenServices;
  @Autowired
  JwtAccessTokenConverter jwtAccessTokenConverter;
  @Autowired
  JWTTokenEnhancer jwtTokenEnhancer;

  @GetMapping(value = "/auth/user", produces = "application/json")
  public Map<String, Object> getUser(OAuth2Authentication authentication) {
    // 上下文可以解析到
    HashMap<String, Object> map = new HashMap<>();
    map.put("users", authentication.getUserAuthentication().getPrincipal());
    map.put("authorities",
        AuthorityUtils.authorityListToSet(authentication.getUserAuthentication().getAuthorities()));
    authentication.getOAuth2Request().getScope();
    return map;
  }

  // 从http手部解析出令牌
  @GetMapping(value = "/jwt/getUser")
  public ResponseEntity<String> getUserWithJWT(HttpServletRequest request) {
    Claims body = getClaims(request);

    String origination = body.get("origination", String.class);

    return ResponseEntity.ok(origination);
  }

  /**
   * 方法重构 Created by hcj on 18-7-24.
   */
  private Claims getClaims(HttpServletRequest request) {
    String authorization = request.getHeader("Authorization");
    String bearer_ = authorization.replace("Bearer ", "");
    return Jwts.parser().setSigningKey(servletConfig.getSignkey().getBytes())
        .parseClaimsJws(bearer_).getBody();
  }

  // 延长时间
//  @PutMapping("addTime")
//  public void addTime(){
//    // 10天
//    defaultTokenServices.setAccessTokenValiditySeconds(60 * 60 * 24 * 10);
//  }

  @PutMapping(value = "/refresh")
  public ResponseEntity<OAuth2AccessToken> refreshToken(
      HttpServletRequest request) {
    Claims body = getClaims(request);
    Long registerTime = body.get("registerTime", Long.class);

    if (Instant.now().toEpochMilli() - registerTime > Constants.TimeIntervel) {

      String s = body.get("refreshToken", String.class);

      Authentication authentication =
          SecurityContextHolder.getContext().getAuthentication();

      OAuth2Authentication authentication1 = (OAuth2Authentication) authentication;
      OAuth2Request oAuth2Request = authentication1.getOAuth2Request();

      // 刷新token ,并替换原来的token信息，
      //  f99c4ea8-5307-4c02-8e99-221e44517b99
      TokenRequest tokenRequest = new TokenRequest(oAuth2Request.getRequestParameters(),
          oAuth2Request.getClientId(), oAuth2Request.getScope(), oAuth2Request.getGrantType());

      OAuth2AccessToken oAuth2AccessToken = defaultTokenServices
          .refreshAccessToken(s, tokenRequest);

      // 生成对应的jwt,额外信息丢了？
      OAuth2AccessToken enhance = jwtAccessTokenConverter
          .enhance(oAuth2AccessToken, authentication1);
      OAuth2AccessToken enhance1 = jwtTokenEnhancer.enhance(enhance, authentication1);

      return ResponseEntity.ok(enhance1);
    }
    return ResponseEntity.ok(null);

  }

//  @GetMapping("status")
//  public ResponseEntity<vo> getStaus(){
//    // 强制下线，用的不多，
//    Authentication authentication =
//        SecurityContextHolder.getContext().getAuthentication();
//    OAuth2Authentication authentication1 = (OAuth2Authentication) authentication;
//    // defaultTokenServices 不维护token，而是交给jwt中的时间来维护。
//    // 服务器自身维护
//
//    return ResponseEntity.ok(accessToken);
//  }

  //@DeleteMapping("delete")
  @PostMapping("/remove/token")
  public ResponseEntity delete(@RequestBody TokenVM tokenVM) {
    // 强制下线，用的不多，
    // 提供一个跳转页面
    boolean b = defaultTokenServices.revokeToken(tokenVM.getToken());

    return ResponseEntity.ok(b);
  }

  // 资源访问是针对客户端的限制，  用户信息，自有检权者， 这里和第三方交互，用到的是clientid？
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/get")
  public ResponseEntity find() {
    // 获取安全上下文,
    Authentication authentication =
        SecurityContextHolder.getContext().getAuthentication();
    OAuth2Authentication authentication1 = (OAuth2Authentication) authentication;
    // 用户权限信息会是null， 只判断客户端权限即可， 第三方服务，
    OAuth2Request oAuth2Request = authentication1.getOAuth2Request();
    Set<String> scope = oAuth2Request.getScope();
    Collection<? extends GrantedAuthority> authorities = oAuth2Request.getAuthorities();

    // 判断该客户端授权的权限信息，
    System.out.println(Arrays.toString(authorities.toArray()));
    // 根据scope, 进行判断
    // session 的维护? 由concurrentHashMap内存， 或者jwt或者redis
    // 并发用户？ ,判断当前用户是否是在注册地进行的登录，不在的话，就要提示用户，
    System.out.println(Arrays.toString(scope.toArray()));

    return ResponseEntity.ok("hello");
  }
  @Bean
  public RestTemplate restTemplate(){
    return new RestTemplate();
  }
  @PreAuthorize("hasRole('USER')")
  @GetMapping("/put")
  public ResponseEntity put() {
    return ResponseEntity.ok("put");
  }

  public static void main(String[] args) {
    SpringApplication.run(OauthjwtApplication.class, args);
  }
}

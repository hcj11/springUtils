package com.example;

import com.example.oauth2.Resource.vm.TokenDetailsVM;
import com.example.oauth2.Security.WebSecurityConfigurer;
import com.example.oauth2.domain.User;
import com.example.oauth2.redis.TokenInfoManage;
import com.example.oauth2.service.UserService;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.oauth2.Resource.vm.TokenVM;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@EnableAuthorizationServer
@EnableResourceServer
public class Oauth2Application {

  // 刷新token 以及管理token操作。
  // 那么就可以重新登录， 或者remember me 自动登录操作，
  @Autowired
  DefaultTokenServices defaultTokenServices;
  @Autowired
  TokenInfoManage tokenInfoManage;
  @Autowired
  WebSecurityConfigurer webSecurityConfigurer;
  @Autowired
  UserService userService;

//  @Autowired

  // 修改用户权限，这个很正常吧, 管理端，或者客户端，比如你购买商品等
  public void updateAuthorization(){
//    defaultTokenServices.getAccessToken()

  }

  @GetMapping("revoke")
  public ResponseEntity<Boolean> revoke(OAuth2Authentication authentication) {
    // 服务器自身维护
    OAuth2AccessToken accessToken = defaultTokenServices.getAccessToken(authentication);
    String value = accessToken.getValue();
    Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();

    boolean b = defaultTokenServices.revokeToken(value);
    return ResponseEntity.ok(b);
  }

  @GetMapping("status")
  public ResponseEntity<OAuth2AccessToken> getStaus(OAuth2Authentication authentication) {
    // 服务器自身维护
    OAuth2AccessToken accessToken = defaultTokenServices.getAccessToken(authentication);
    return ResponseEntity.ok(accessToken);
  }

  // 固定延长时间
  @PutMapping("addTime")
  public ResponseEntity addTime(OAuth2Authentication authentication) {
    // todo 直接操作redis 全部keys* 进行延长时间
    OAuth2AccessToken accessToken = defaultTokenServices.getAccessToken(authentication);
    return ResponseEntity.ok("增加刷新时间成功");
  }

  //auth/fresh 直接刷新来重置token，使另一个token失效
  @PutMapping(value = "/auth/fresh")
  public ResponseEntity<OAuth2AccessToken> refreshAccessToken(@RequestBody TokenDetailsVM tokenVM,
      OAuth2Authentication authentication) throws Exception {
    OAuth2Request oAuth2Request = authentication.getOAuth2Request();
    TokenRequest tokenRequest = new TokenRequest(oAuth2Request.getRequestParameters(),
        oAuth2Request.getClientId(), oAuth2Request.getScope(), oAuth2Request.getGrantType());
    AuthenticationManager authenticationManager = webSecurityConfigurer.authenticationManager();
    //
    OAuth2AccessToken accessToken = defaultTokenServices.getAccessToken(authentication);

    defaultTokenServices.setAuthenticationManager(authenticationManager);
    User byAccessToken = userService.findByAccessToken(accessToken.getValue());
    if(byAccessToken==null){
      throw new RuntimeException("不存在该用户");
    }
    byAccessToken.setRoleId(tokenVM.getAuthority());
    userService.save(byAccessToken);


    OAuth2AccessToken oAuth2AccessToken = defaultTokenServices
        .refreshAccessToken(tokenVM.getRefreshToken(), tokenRequest);

    // 更新token
    byAccessToken.setAccessToken(oAuth2AccessToken.getValue());
    byAccessToken.setRefreshToken(oAuth2AccessToken.getRefreshToken().getValue());
    userService.save(byAccessToken);


    return ResponseEntity.ok(oAuth2AccessToken);

  }

  // 校验:
  // ResponseEntity<Map<String,Object>>
  @GetMapping(value = "/auth/user", produces = "application/json")
  public Map<String, Object> getUser(OAuth2Authentication authentication) {
    HashMap<String, Object> map = new HashMap<>();
//    DefaultOAuth2AccessToken accessToken1 = (DefaultOAuth2AccessToken) accessToken;
    authentication.getOAuth2Request().getExtensions();

    OAuth2AccessToken accessToken = defaultTokenServices.getAccessToken(authentication);
    Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();
    System.out.println(additionalInformation);

    map.put("users", authentication.getUserAuthentication().getPrincipal());
    map.put("userauthorities",
        AuthorityUtils.authorityListToSet(authentication.getUserAuthentication().getAuthorities()));
//    System.out.println(authentication.getOAuth2Request().getAuthorities());
    map.put("authorities", authentication.getOAuth2Request().getAuthorities());

    return map;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/get")
  public ResponseEntity find() {
    // 获取安全上下文,
    Authentication authentication =
        SecurityContextHolder.getContext().getAuthentication();
    OAuth2Authentication authentication1 = (OAuth2Authentication) authentication;
    System.out.println(authentication1.getUserAuthentication().getAuthorities());

    System.out.println(authentication1.getOAuth2Request().getAuthorities());
    OAuth2Request oAuth2Request =
        authentication1.getOAuth2Request();
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

  // 用户的权限进行的校验，
  @PreAuthorize("hasRole('USER')")
  @PutMapping("/put")
  public ResponseEntity put() {
    return ResponseEntity.ok("put");
  }

  public static void main(String[] args) {
    SpringApplication.run(Oauth2Application.class, args);
  }
}

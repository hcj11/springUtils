package com.example.oauthjwt.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletContext;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by hcj on 18-8-10
 */
@RestController
public class UserResource {

  @Autowired
  ServletContext servletContext;
  @Autowired
  RestTemplate restTemplate;


  @GetMapping(value = "getCurrentUser")
  public ResponseEntity getCurrentUser() {
    Authentication authentication =
        SecurityContextHolder.getContext().getAuthentication();
    if (authentication.getPrincipal().equals("anonymousUser")) {
      ErrorVm<String> errorVm = new ErrorVm<>("登录失败!!!", null, "");
      return ResponseEntity.ok(errorVm);
    }
    String name = authentication.getPrincipal().toString();
    // 获取用户信息
    SuccessVm<String> userSuccessVm = new SuccessVm<>("", null, name);

    return ResponseEntity.ok(userSuccessVm);
  }


  @PostMapping("login")
  public ResponseEntity login(String username, String password, String grant_type, String scope) {
//    HttpServletRequest request1 = (HttpServletRequest) request; ServletRequest request,
//    System.out.println(request1.getHeader("Authorization"));
    String url = "http://localhost:8081/api/oauth/token";
    // 获取参数,并进行校验
    String format = String.format("%s,%s,%s,%s", username, password, grant_type, scope);
    System.out.println(format);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.set("Authorization", "Basic ZWFnbGVleWU6MTIz");
    MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
    map.add("username", username);
    map.add("grant_type", grant_type);
    map.add("scope", scope);
    map.add("password", password);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
        map, headers);

    ResponseEntity<OAuth2AccessToken> response = restTemplate
        .postForEntity(url, request, OAuth2AccessToken.class);

    if (!response.getStatusCode().equals(HttpStatus.OK)) {
      ErrorVm<String> errorVm = new ErrorVm<String>("登录失败", null, "");
      return ResponseEntity.ok(errorVm);
    }

    OAuth2AccessToken body = response.getBody();
    String value = body.getValue();
    Map<String, Object> additionalInformation = body.getAdditionalInformation();
    LinkedHashMap username1 = (LinkedHashMap) additionalInformation.get("username");
    String object = (String) username1.get("username");

    DataVM dataVM = new DataVM();
    // 昵称，
    dataVM.setUsername(object);
    dataVM.setToken(value);

    SuccessVm<DataVM> userSuccessVm = new SuccessVm<DataVM>("", null, dataVM);
    userSuccessVm.setCode(2);
    return ResponseEntity.ok(userSuccessVm);
  }

}

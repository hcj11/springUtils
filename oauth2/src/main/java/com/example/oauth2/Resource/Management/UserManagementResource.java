package com.example.oauth2.Resource.Management;

import com.example.oauth2.Resource.vm.TokenDetailsVM;
import com.example.oauth2.Resource.vm.TokenVM;
import com.example.oauth2.redis.redistemplate.StringUtil;
import com.example.oauth2.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hcj on 18-7-28
 */
@RestController
@RequestMapping("/manage/user")
public class UserManagementResource  {


  @Autowired
  StringUtil stringUtil;
  @Autowired
  UserService userService;

//  @GetMapping
//  public ResponseEntity findAll(){
//    stringUtil.findAllAuth();
//
//  }

  // 增加用户
  @PostMapping
  public void  add(){

  }

//  @PutMapping
//  public void updateAuthrozition(@RequestBody TokenDetailsVM tokenDetailsVM){
//    userService.updateAuthrozition(tokenDetailsVM);
//  }

  // 删除，根据accessToken ,remove
  @DeleteMapping
  public void delete(@RequestBody TokenVM tokenVM){

//    defaultTokenServices.revokeToken(tokenVM.getAccessToken());
  }


}

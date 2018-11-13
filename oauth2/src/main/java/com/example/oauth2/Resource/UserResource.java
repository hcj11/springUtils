package com.example.oauth2.Resource;

import com.example.oauth2.domain.User;
import com.example.oauth2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hcj on 18-7-26
 */
@RestController
@RequestMapping("/user")
public class UserResource {

  @Autowired
  private UserService userService;


  @PostMapping
  public ResponseEntity register(@RequestBody User user) {
    userService.save(user);

    return ResponseEntity.ok("注册成功");
  }

}

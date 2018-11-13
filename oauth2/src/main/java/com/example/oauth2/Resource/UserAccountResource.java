package com.example.oauth2.Resource;

import com.example.oauth2.service.UserAccountService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hcj on 18-7-29
 */
@RestController
@RequestMapping("userAccount")
public class UserAccountResource {

  @Autowired
  private UserAccountService userAccountService;


  @GetMapping
  public List<UserAccount> getAll() {
    return userAccountService.findAll();
  }

  @PostMapping
  public void save(@RequestBody UserAccount userAccount) {
    userAccountService.save(userAccount);
  }

  @PutMapping("{id}")
  public void update(@PathVariable("id") Long id, @RequestBody UserAccount userAccount) {
    userAccount.setId(id);
    userAccountService.update(userAccount);
  }


}

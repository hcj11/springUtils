package com.example.demo.web;

import com.example.demo.domain.User;
import com.example.demo.security.SecurityUtils;
import com.example.demo.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 管理端,
 * Created by hcj on 18-7-22
 */
@Controller
@RequestMapping("/management/user")
public class UserManagementResource {

  @Autowired
  private UserService userService;
  @Autowired
  ApplicationContext context;

  // 用户展示
  @GetMapping
//  @PreAuthorize(value = "hasRole('ROLR_USER')")
  public String userLists(Model model) {
    String s = SecurityUtils.getCurrentUserLogin().orElse(null);
    List<User> users = userService.findUsers();
    model.addAttribute("users", users);
    model.addAttribute("user",s);

    return "userLists";
  }

  @GetMapping("{id}")
  public String findOne(@PathVariable(value = "id") String id, Model model) {
    User one = userService.findOne(Long.parseLong(id));
    model.addAttribute("user", one);
    return "userOne";
  }

  @PostMapping
  public String addUsers(User user) {
    User save = userService.save(user);
    Long id = save.getId();
    return "redirect:user/" + id + "";
  }


}

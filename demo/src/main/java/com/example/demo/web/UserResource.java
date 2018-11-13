package com.example.demo.web;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 移动端注册,
 * Created by hcj on 18-7-22
 */
@Controller
@RequestMapping
public class UserResource {

    @Autowired
    private UserService userService;
    @Autowired
    private HomeResource homeResource;
    @Autowired
    private UserResource userResource;


    // 用户展示
//  @GetMapping
//  public String userLists(Model model) {
//    List<User> users = userService.findUsers();
//    model.addAttribute("users", users);
//    return "userLists";
//  }
//
    @GetMapping("{id}")
    public String findOne(@PathVariable(value = "id") String id, Model model) {
        User one = userService.findOne(Long.parseLong(id));
        model.addAttribute("user", one);
        return "userOne";
    }

    @ResponseBody
    @Transactional
    @PostMapping("register2")
    public String addUsers2(User user) {
        // 校验事务回滚。  事务的传播行为。若存在则继续使用。 对于
        User save1 = userService.save(user);
        return "hello";
    }
    @Transactional
    @ResponseBody
    @PostMapping("register")
    public String addUsers(User user) {
        // 校验事务回滚。  事务的传播行为。若存在则继续使用。 对于 内部调用o的情况，观察是否回滚
        // 一次add  会回滚
        User save1 = userService.save(user);
        // 二次add  父方法中没有开启事务。本地调用,不会回滚 ，
        // 同理对于service层，内部调用需要在父方法中添加事务才会有效,而对于字方法而言都是在总事务下的方法调用
        // 对于事务未回滚的情况单独说明，以上情况都是符合
        userResource.addUsers2(user);
        Long id = save1.getId();
        int i = 1 / 0;
        return "redirect:user/" + id + "";
    }

//    @Transactional
//    @PostMapping("register")
//    public String addUsers(User user) {
//        // 校验事务回滚。  事务的传播行为。若存在则继续使用。 对于
////        User save1 = homeResource.save(user);
//        userResource
//        User save1 = userService.save(user);
//        Long id = save1.getId();
//        int i = 1 / 0;
//        return "redirect:user/" + id + "";
//    }
}

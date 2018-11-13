package com.example.demo.web;

import com.example.demo.config.CustomArg;
import com.example.demo.config.CustomRequestMapping;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import com.example.demo.sync.AysncApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/test")
public class TestResource {
    @Autowired
    private UserService userService;
    @Autowired
    private AysncApp aysncApp;


    @Async(value = "CustomThreadPoolTaskExecutor")
    public void get5() throws ExecutionException, InterruptedException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 插入。
        System.out.println(userService.findOne(1L));

        CompletableFuture<String> future = CompletableFuture.completedFuture("");
//
//        // 等待不相干的任务结束
//        CompletableFuture.allOf(future).join();
//        System.out.println(future.get());



    }


    // 无法解析返回的参数，
    @CustomRequestMapping(value = "{id}")
    public User get1(@PathVariable(value = "id") String id) {
        aysncApp.get4();
//        get5();
        System.out.println("返回 。。。");
        return userService.findOne(Long.parseLong(id));
    }

    // content-type-> consumes   |  Accept-> produces 生产。。数据 ,produces = {"application/xml"}

    @ResponseBody
    @RequestMapping(value = "/get2/{id}", produces = {"application/xml"})
    public User get2(@PathVariable(value = "id") String id) {
        return userService.findOne(Long.parseLong(id));
    }

    // 获取请求体
    @ResponseBody
    @RequestMapping(value = "/get3")
    public String get3(@RequestBody String text) {
        return text;
    }

    @ResponseBody
    @GetMapping(value = "/get4")
    public String get4(@CustomArg User user) {
        System.out.println(user.getLogin());
        return "hello world";
    }

    @GetMapping(value = "/get5")
    public User get5(@CustomArg User user) {
        System.out.println(user.getLogin());
        return user;
    }
}

package com.example.demo.sync;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
@EnableAsync
@Service
public class AysncApp {
    @Autowired
    private UserService userService;

    @Async(value = "CustomThreadPoolTaskExecutor")
    public void get4() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 插入。
        System.out.println(userService.findOne(1L));

    }
}

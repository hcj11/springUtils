package com.example.redisdemo;

import com.example.redisdemo.Test.LockDemo1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/lock")
public class RedisdemoApplication {

    @Autowired
    LockDemo1 lockDemo1;

    @GetMapping
    public void demoLock() {

        lockDemo1.addOrder(1);
        System.out.println("计算结果： " + lockDemo1.getCount());
//    lockDemo1.setCount(0);
    }

    @GetMapping("/look")
    public String look() {

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + ":" + i);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "111";
    }

    public static void main(String[] args) {
        SpringApplication.run(RedisdemoApplication.class, args);
    }
}

package com.example.demo;

import com.example.demo.config.observable.UserUpdateAccept;
import com.example.demo.config.observable.UserUpdateAccept2;
import com.example.demo.config.observable.UserUpdateResponse;
import com.example.demo.config.observable.UserUpdateResponseImpl;
import com.example.demo.domain.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class DemoApplication {
    @Autowired
    private UserUpdateAccept userUpdateAccept;
    @Autowired
    private UserUpdateAccept2 userUpdateAccept2;

    @Bean
    public UserUpdateResponseImpl UserUpdateResponseImpl() {
        UserUpdateResponseImpl userUpdateResponse = new UserUpdateResponseImpl();
        userUpdateResponse.addObserver(userUpdateAccept);
        userUpdateResponse.addObserver(userUpdateAccept2);
        return userUpdateResponse;
    }

    public static void main(String[] args) {


        ConfigurableApplicationContext run =
                SpringApplication.run(DemoApplication.class, args);

//        UserEvent userEvent = new UserEvent("hello world", "1");
//        // 获取接口的所有的实现类，并循环发布。 或者使用子类即可
//
////        UserUpdateListener bean = run.getBean(UserUpdateListener.class);
//        UserUpdateResponse bean = run.getBean(UserUpdateResponse.class);
//
//        bean.updateUser(userEvent);
    }


}

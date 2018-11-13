package com.example.demo.config;

import com.example.demo.config.interf.UserUpdateListener;
import com.example.demo.domain.User;
import com.example.demo.domain.UserEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserUpdateListenerImpl implements UserUpdateListener {

    // 根据id, 设置参数
    @Transactional
    @Override
    public void updateUser(UserEvent userEvent) {
        System.out.println(userEvent);
    }
}

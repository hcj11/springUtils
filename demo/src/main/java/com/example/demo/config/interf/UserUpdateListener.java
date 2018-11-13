package com.example.demo.config.interf;


import com.example.demo.domain.UserEvent;

import java.util.EventListener;

public interface UserUpdateListener extends EventListener {
    public void updateUser(UserEvent user);
}

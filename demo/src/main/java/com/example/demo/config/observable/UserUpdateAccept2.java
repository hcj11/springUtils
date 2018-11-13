package com.example.demo.config.observable;

import org.springframework.stereotype.Component;

import java.util.Observable;
import java.util.Observer;
@Component
public class UserUpdateAccept2 implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("UserUpdateAccept2得到通知: " + arg);
    }
}

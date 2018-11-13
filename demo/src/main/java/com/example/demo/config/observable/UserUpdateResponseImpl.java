package com.example.demo.config.observable;

import com.example.demo.domain.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Observable;

public class UserUpdateResponseImpl extends Observable implements UserUpdateResponse {



    @Override
    public void updateUser(UserEvent userEvent) {
        super.setChanged();
        super.notifyObservers(userEvent);
    }
}

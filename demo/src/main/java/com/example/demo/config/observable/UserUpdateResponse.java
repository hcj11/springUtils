package com.example.demo.config.observable;

import com.example.demo.domain.UserEvent;

public interface UserUpdateResponse {
    public void updateUser(UserEvent userEvent);
}

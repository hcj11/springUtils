package com.example.demo.domain;

import lombok.Data;

@Data
public class UserEvent {
    private String username;
    private String id;

    public UserEvent(String username, String id) {
        this.username = username;
        this.id = id;
    }
}

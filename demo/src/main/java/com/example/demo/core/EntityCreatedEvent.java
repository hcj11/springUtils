package com.example.demo.core;

import com.example.demo.domain.User;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpSession;

/**
 * Create by houchunjian on 2018/10/15 0015
 */
public class EntityCreatedEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public EntityCreatedEvent(Object source) {
        super(source);
        // 执行其他
        getEvent(source);
    }

    public void getEvent(Object source) {
        System.out.println(source);
    }

    public User getUser() {
        return (User) getSource();
    }
}

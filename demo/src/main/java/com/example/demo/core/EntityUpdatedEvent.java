package com.example.demo.core;

import org.springframework.context.ApplicationEvent;

/**
 *  Create by houchunjian on 2018/10/15 0015
 */
public class EntityUpdatedEvent extends ApplicationEvent {
    /**
     * updated a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
            */
    public EntityUpdatedEvent(Object source) {
        super(source);

        // service 调用。
    }

    public String getEvent(Object source){
        return (String)source;
    }
}

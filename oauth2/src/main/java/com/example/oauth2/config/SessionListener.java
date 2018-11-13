package com.example.oauth2.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by hcj on 18-7-29
 */
@Component
public class SessionListener implements ApplicationListener<SessionDestroyedEvent> {


  @Override
  public void onApplicationEvent(SessionDestroyedEvent event) {
    System.out.println(event);
  }
}


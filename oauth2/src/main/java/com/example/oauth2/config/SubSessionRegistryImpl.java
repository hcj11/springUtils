package com.example.oauth2.config;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Component;

/**
 * Created by hcj on 18-7-29
 */
@Component
public class SubSessionRegistryImpl extends SessionRegistryImpl {

  @Override
  public SessionInformation getSessionInformation(String sessionId) {
    System.out.println("sessionId:" +sessionId);
    return super.getSessionInformation(sessionId);
  }
}

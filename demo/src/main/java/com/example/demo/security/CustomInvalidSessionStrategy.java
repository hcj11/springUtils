package com.example.demo.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

/**
 * Created by hcj on 18-7-23
 */
@Configuration
public class CustomInvalidSessionStrategy implements InvalidSessionStrategy {
  private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
  private  String destinationUrl;
  public CustomInvalidSessionStrategy(){}
  public CustomInvalidSessionStrategy(String invalidSessionUrl) {
    Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl),
        "url must start with '/' or with 'http(s)'");
    this.destinationUrl = invalidSessionUrl;
  }

  @Override
  public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    /**
     * Returns the current session associated with this request,
     * or if the request does not have a session, creates one.
     * Created by hcj on 18-7-23.
     */
    // 根据 request进行拦截
//      HttpSession session = request.getSession();
//      String id = session.getId();
//      System.out.println("sessionId: "+id);
//      redirectStrategy.sendRedirect(request, response, destinationUrl);
       redirectStrategy.sendRedirect(request, response, request.getRequestURI());
  }
}

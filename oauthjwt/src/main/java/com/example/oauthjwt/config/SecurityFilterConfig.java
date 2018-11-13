package com.example.oauthjwt.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Created by hcj on 18-7-24
 */
public class SecurityFilterConfig implements Filter {
  @Autowired
  ApplicationContext context;

  @Override
  public void init(javax.servlet.FilterConfig filterConfig)  {
    // 两次初始化.
    System.out.println("初始化,");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    // 添加到过滤链中,
    // 做request的拦截
    HttpServletRequest request1 = (HttpServletRequest) request;
    HttpServletResponse response1 = (HttpServletResponse) response;
    // 重定向
    HttpServletResponseWrapper httpServletResponseWrapper = new HttpServletResponseWrapper(
        response1);
    httpServletResponseWrapper.sendRedirect(request1.getRequestURI());

  }

  @Override
  public void destroy() {
    System.out.println("结束,");
  }
}

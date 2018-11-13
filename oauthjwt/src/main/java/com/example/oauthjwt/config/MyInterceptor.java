package com.example.oauthjwt.config;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


// 定义执行顺序.
//@Order(value = 100)
@Component
public class MyInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      {
    // 拦截 / 修改拦截的路径 添加拦截路径
    System.out.println("预处理");
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
//    response.getOutputStream();
    ServletOutputStream outputStream = response.getOutputStream();
    // 组装？
    System.out.println(outputStream.toString());

    System.out.println("后置处理");
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    System.out.println("完成.");
  }
}
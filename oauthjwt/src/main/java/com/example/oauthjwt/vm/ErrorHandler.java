package com.example.oauthjwt.vm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * Created by hcj on 18-8-10
 */
@Component
public class ErrorHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
      System.out.println(exception.getCause().getMessage());
//       ErrorVm<String> vm = new ErrorVm<String>("登录失败!!!", null, "");
       // 返回
       response.sendRedirect("http://localhost:8080/");

  }
}

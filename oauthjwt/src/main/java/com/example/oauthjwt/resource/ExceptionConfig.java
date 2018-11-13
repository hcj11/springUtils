package com.example.oauthjwt.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by hcj on 18-8-9
 */
@RestControllerAdvice
public class ExceptionConfig {

  @ExceptionHandler(value = InvalidRequestException.class)
  public ResponseEntity<ErrorVm> InvalidRequestExceptionResolve() {

    ErrorVm<String> vm = new ErrorVm<String>("登录失败!!!", null, "");

    return ResponseEntity.ok(vm);
  }

}
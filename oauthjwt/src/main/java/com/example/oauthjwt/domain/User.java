package com.example.oauthjwt.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by hcj on 18-8-10
 */
@Setter
@Getter
@ToString
public class User {
  private String username;
  private String password;

}

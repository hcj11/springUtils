package com.example.oauth2.Resource.vm;

import lombok.Getter;
import lombok.Setter;


/**
 * Created by hcj on 18-7-24
 */
@Setter
@Getter
public class TokenVM {

  private String accessToken;
  private String refreshToken;
}

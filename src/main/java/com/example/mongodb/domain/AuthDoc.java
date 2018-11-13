package com.example.mongodb.domain;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 授权信息
 * Created by hcj on 18-8-5
 */
@Setter
@Getter
@ToString
public class AuthDoc {
    private Instant ts= Instant.now();
    private Long  cc;
    private Long  id;
    private String gateway;

  public AuthDoc( Long cc, Long id, String gateway) {
    this.cc = cc;
    this.id = id;
    this.gateway = gateway;
  }
}

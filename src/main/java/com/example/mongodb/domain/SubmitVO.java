package com.example.mongodb.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by hcj on 18-8-5
 */
@Setter
@Getter
public class SubmitVO {
  private String user_id;
  private  Double sub_total;
}

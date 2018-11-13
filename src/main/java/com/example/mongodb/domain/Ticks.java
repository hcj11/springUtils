package com.example.mongodb.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by hcj on 18-8-3
 */
@Setter
@Getter
@ToString
@Document
public class Ticks {
  // 股票
  @Id
  private Details details;
  private String name;
}

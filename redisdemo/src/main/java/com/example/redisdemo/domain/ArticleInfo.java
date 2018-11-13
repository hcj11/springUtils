package com.example.redisdemo.domain;

import java.io.Serializable;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 定义文章内容，的hash
 * Created by hcj on 18-7-26
 */
@Setter
@Getter
@ToString
public class ArticleInfo implements Serializable {
  private static final long serialVersionUID = 1L;
  // 用户其他信息
  private Instant time = Instant.now();
  private String  articleUser;
  // 发布的内容
  private String content;

}

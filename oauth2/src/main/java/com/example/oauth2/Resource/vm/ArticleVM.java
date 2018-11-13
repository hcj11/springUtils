package com.example.oauth2.Resource.vm;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by hcj on 18-7-26
 */
@Setter
@Getter
public class ArticleVM {
  // 目前先这么多
  // 额外信息
  private String username;
  // 文章相关的页面信息
  private String intro;
  private String url;
  private String time;
  // 分值
  private String score;


}

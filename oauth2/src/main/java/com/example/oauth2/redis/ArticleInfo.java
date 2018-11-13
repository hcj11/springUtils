package com.example.oauth2.redis;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hcj on 18-7-26
 */
@Setter
@Getter
public class ArticleInfo implements Serializable{
  public static final long serialVersionUID = 1L;
  private String intro;
  private String url;
  private String time;
  private String username;

  public ArticleInfo(String intro, String url, String time,String username) {
    this.intro = intro;
    this.url = url;
    this.time = time;
    this.username=username;
  }
}

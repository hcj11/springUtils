package com.example.mongodb.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashSet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 评价 Created by hcj on 18-8-3
 */
@Setter
@Getter
@ToString
@Document
public class Review implements Serializable{
  public static final long serialVersionUID = 42L;
  @Id
  private String id;
  private String product_id;
  private Instant date = Instant.now();
  private String title;
  private String text;
  // 评分 0,1,2,3,4,5
  private Double rating;
  private String user_id;
  private String username;// 冗余
  private Double helpful_votes;
  private Collection<String> voter_ids = new LinkedHashSet<>();


}

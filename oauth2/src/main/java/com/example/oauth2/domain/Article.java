package com.example.oauth2.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * 文章持久化到数据库
 * Created by hcj on 18-7-26
 */
@Setter
@Getter
@Entity
@Table(name = "jhi_article")
public class Article extends AbstractAuditingEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "article_name",length = 40)
  private String articleName;

  @Column
  private String content;

  @Column
  private String intro;

  @Column
  private String authorname;

  @Column
  private Long userId;
  public Article(){}
  public Article(String articleName, String content, String intro, String authorname,
      Long userId) {
    this.articleName = articleName;
    this.content = content;
    this.intro = intro;
    this.authorname = authorname;
    this.userId = userId;
  }
}
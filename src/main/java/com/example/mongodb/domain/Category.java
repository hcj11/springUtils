package com.example.mongodb.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 类目 Created by hcj on 18-8-3
 */
@Setter
@Getter
@ToString
@Document
public class Category implements Serializable{
  public static final long serialVersionUID = 42L;
  @Id
  private String id;
  // 链接标识
  private String slug;
  private Collection<Category> ancestors = new LinkedHashSet<Category>();
   // 联合主键
  private String parent_id;
  private String name;
  private String description;
  public Category(){}
  public Category(String slug, String name, String description) {
    this.slug = slug;
    this.name = name;
    this.description = description;
  }

  public Category(String slug, Collection<Category> ancestors, String name,
      String description) {
    this.slug = slug;
    this.ancestors = ancestors;
    this.name = name;
    this.description = description;
  }
}

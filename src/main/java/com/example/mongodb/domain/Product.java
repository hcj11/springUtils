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
 * Created by hcj on 18-8-3
 */
@Setter
@Getter
@ToString
@Document
public class Product implements Serializable{
  public static final long serialVersionUID = 42L;

  @Id
  private String id;
  // 商品链接编号
  private String slug;
  // 商品的最小单元编号
  private String sku;
  private String name;
  private String description;
  private Details details;
  private Double total_reviews;
  private Double average_reviews;
  private Price price;
  private Collection<Price> price_history = new LinkedHashSet<Price>();
  private Collection<String> category_ids = new LinkedHashSet<String>();
  private String main_category_id;
  private Collection<String> tags = new LinkedHashSet<String>();


}

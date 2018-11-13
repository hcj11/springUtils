package com.example.mongodb.domain;

import com.example.mongodb.domain.enumVO.STATE;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashSet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by hcj on 18-8-4
 */
@Setter
@Getter
@ToString
public class CartItem implements Serializable {
  public  static final long serialVersionUID = 42L;;

  private String id;
  private String slug;
  private String sku;
  private String name;
  private Double quantity;
  private Price price = new Price();
}

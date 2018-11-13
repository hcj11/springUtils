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
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 用户下订单,
 * Created by hcj on 18-8-3
 */
@Setter
@Getter
@ToString
@Document
public class Order implements Serializable{
  public static final long serialVersionUID = 42L;
  @Id
  private String id;
  private String user_id;
  @Field("purchase_date")
  private Instant purchaseDate = Instant.now();
  private STATE state;
  @Field("line_items")
  private Collection<ItemVM> lineItems = new LinkedHashSet<ItemVM>();
  private Collection<Address> shipping_address = new LinkedHashSet<Address>();
  // 总计
  @Field("sub_total")
  private Double subTotal;
}

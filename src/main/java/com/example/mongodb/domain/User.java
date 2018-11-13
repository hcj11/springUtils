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
public class User implements Serializable{
  public static final long serialVersionUID = 42L;

  @MongoAutoId(value = User.class)
  private Integer id;
  private String username;
  private String first_name;
  private String last_name;
  private String hash;
  private Collection<Address> addresses =  new LinkedHashSet<Address>();
  private Collection<String> payment_methods =  new LinkedHashSet<String>();

}

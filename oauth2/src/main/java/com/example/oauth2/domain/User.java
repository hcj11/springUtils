package com.example.oauth2.domain;

import com.example.oauth2.config.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * pojo Created by hcj on 18-7-22
 */
@Setter
@Getter
@Entity
@Table(name = "access_user")
public class User extends AbstractAuditingEntity implements Serializable {

  private static final long serialVersionUID = 1L;


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Pattern(regexp = Constants.LOGIN_REGEX)
  @Size(min = 1, max = 50)
  @Column(length = 50, unique = true, nullable = false)
  private String login;

//  @JsonIgnore 无法被序列化。入参
  @NotNull
  @Size(min = 1, max = 60)
  @Column(name = "password_hash", length = 60)
  private String password;

  @Size(max = 50)
  @Column(name = "first_name", length = 50)
  private String firstName;

  // 明确定义长度
  @Size(max = 50)
  @Column(name = "last_name", length = 50)
  private String lastName;

  // 一对多级联查询 , 或者查询两次?
//  private List<Role> roleList;
  @Column(name = "role_id", length = 20)
  private Long roleId;

  @Column(name = "access_token")
  private String accessToken;

  @Column(name = "refresh_token")
  private String refreshToken;

}

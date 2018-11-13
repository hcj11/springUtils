package com.example.oauth2.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hcj on 18-7-22
 */
@Setter
@Getter
@Entity
@Table(name = "jhi_role")
public class Role implements Serializable{

  // 角色 <---> 权限[管理]
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(max = 50)
  @Column(name = "first_name", length = 50)
  private String rolename;
}

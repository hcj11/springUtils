package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by hcj on 18-7-22
 */
@Setter
@Getter
@Entity
@Table(name = "jhi_role")
public class Role {

  // 角色 <---> 权限[管理]
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(max = 50)
  @Column(name = "first_name", length = 50)
  private String rolename;
}

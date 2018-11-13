package com.example.oauth2.Resource;

import com.example.oauth2.config.Constants;
import com.example.oauth2.domain.AbstractAuditingEntity;
import com.example.oauth2.domain.Status;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * Created by hcj on 18-7-29
 */
@Setter
@Getter
@ToString
@Entity
@Table(name = "user_account")
public class UserAccount extends AbstractAuditingEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 50, unique = true, nullable = false)
  private String  money;

  @Column
  @Enumerated(value = EnumType.STRING)
  private Status status = Status.SMALL;

  @Column
  private Integer integration;

}

package com.example.oauthjwt.resource;

import com.example.oauthjwt.resource.util.PageVM;
import com.google.common.primitives.Ints;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Builder.ObtainVia;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by hcj on 18-8-10
 */
@Setter
@Getter
@ToString
//@Builder
@Entity
@Table(name = "tx_test")
public class test {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ObtainVia(method = "this.genetityId()")
  private Long id;

  private Long genetityId() {
    return  1L;
  }

  @Column
  private String name;
  @Column
  private Long weight;
  @Column
  private Long gender;
  @Column
  private String marriage;
  // 吃饭,睡觉,打豆豆,
  @Column
  private String interest;
  // 优点: 懒,宅
  @Column
  private String good;
  @Column
  private String pic1;
  @Column(name = "description")
  private String desc;
  @Column
  private Long score;
  @Column
  private Double gpa;
  // 生日
  @Column
  private LocalDateTime birthday;
  // 某个时间
  @Column
  private LocalDateTime xxday;
  public test(){

  }




}

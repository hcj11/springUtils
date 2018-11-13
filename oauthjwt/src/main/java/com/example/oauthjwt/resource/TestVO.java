package com.example.oauthjwt.resource;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 * VO例子
 *
 * @author foolbeargm@gmail.com
 * @version 1.0
 */
@Data
public class TestVO {

  private Long id;
  private String name;
  private Long weight;
  private Long gender;
  private String marriage;
  // 返回数组,
  private List<Long> interest;
  private List<String> good;
  private String pic1;
  private String desc;
  private Long score;
  private Double gpa;
  private String birthday;
  private String xxday;

  //  private LocalDate birthday;
//  private Instant xxday;

}

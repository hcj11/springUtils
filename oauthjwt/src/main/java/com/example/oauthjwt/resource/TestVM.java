package com.example.oauthjwt.resource;

import com.example.oauthjwt.resource.util.PageVM;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * 查询条件 ,  根据是否为null判断,
 * Created by hcj on 18-8-11
 */
@Setter
@Getter
public class TestVM extends PageVM{
  
  private String name;
  
  private Integer weight;
  
  private Integer gender;
  
  private String marriage;
  
  private Integer interest;
  
  private String good;
  
  private String pic1;
  
  private String desc;
  
  private Integer score;
  
  private Float gpa;
  // 生日
  private LocalDate birthday;
  // 某个时间
  
  private LocalDateTime xxday;
}

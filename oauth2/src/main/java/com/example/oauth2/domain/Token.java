package com.example.oauth2.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by hcj on 18-7-25
 */
@Setter
@Getter
@ToString
public class Token implements Serializable{
  private static final long serialVersionUID =1L;

  private Long id=1L;
  private Instant expire = Instant.now();
  private List<String> otherList = new ArrayList<>();
  private Map<String,Object> otherMap = new HashMap<String,Object>();
  private Details details = new Details();

  @Setter
  @Getter
  @ToString
  private class Details implements Serializable{
    // 依赖类也要进行制定serialVersionUID
    private static final long serialVersionUID =1L;
    private String content="hello";
  }

}

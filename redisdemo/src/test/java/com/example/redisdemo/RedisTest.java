package com.example.redisdemo;

import com.example.redisdemo.domain.Token;
import java.util.List;
import java.util.Set;

/**
 * Created by hcj on 18-7-25
 */
public interface RedisTest {
  public Token select();
  public Set<String> selectTokenListWithPartten(String key);
  public void add();
  public void update();
  public void del();
  public List<Token> selectTokenWithCollection() ;
}

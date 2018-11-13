package com.example.redisdemo.redis;


import com.example.redisdemo.domain.Token;
import java.util.List;
import java.util.Set;

/**
 * Created by hcj on 18-7-25
 */
public interface TokenManageTest {
  public void   addToken(List<Token> tokens);
  public Token  selectToken(String key);
  public void   deleteToken(String id);
  public void   updateToken(List<Token> tokens);
  public Set<String> selectTokenListWithPartten(String key);

}

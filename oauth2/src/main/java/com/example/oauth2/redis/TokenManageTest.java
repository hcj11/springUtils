package com.example.oauth2.redis;

import com.example.oauth2.domain.Token;

/**
 * Created by hcj on 18-7-25
 */
public interface TokenManageTest {
  public void  addToken(Token user);
  public Token  selectToken();
  public void  deleteToken(int id);
  public void  updateTokne();
}

package com.example.redisdemo;

import com.example.redisdemo.domain.Token;
import com.example.redisdemo.redis.StringTest;
import com.example.redisdemo.redis.commonTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisStringTests {

  @Autowired
  StringTest stringTest;
  @Autowired
  commonTest commontest;
  private final String TOKENSTR = "tokenstr:";

  @Test
  public void addTime(){
    commontest.addTime();
  }

  @Test
  public void select() {
    System.out.println(stringTest.selectToken(TOKENSTR + "0"));
  }

  @Test
  public void selectTokenWithCollection() {
    Set<String> strings = stringTest.selectTokenListWithPartten(TOKENSTR);
    List<Token> tokens = stringTest.selectTokenWithCollection(strings);
    System.out.println();
  }

  @Test
  public void deleteCollections() {
    Set<String> strings = stringTest.selectTokenListWithPartten(TOKENSTR);
    stringTest.deleteTokens(strings);
  }

  @Test
  public void selectTokenListWithPartten() {
    System.out.println(stringTest.selectTokenListWithPartten(TOKENSTR));
  }
  @Test
  public void addTokenWithPipeline(){
    ArrayList<Token> objects = getTokens(50, 60);
    stringTest.addTokenWithPipeline(objects);
  }

  @Test
  public void add() {
    ArrayList<Token> objects = getTokens(40, 50);

    stringTest.addToken(objects);
  }

  private ArrayList<Token> getTokens(int i2, int i3) {
    ArrayList<Token> objects = new ArrayList<>();
    for (int i = i2; i < i3; i++) {
      Token tokenManageTest = new Token();
      tokenManageTest.setId((long) i);
      List<String> otherList =
          tokenManageTest.getOtherList();
      otherList.add("1");
      Map<String, Object> otherMap = tokenManageTest.getOtherMap();
      otherMap.put("1", "2");
      objects.add(tokenManageTest);
    }
    return objects;
  }

  @Test
  public void update() {

    ArrayList<Token> objects = getTokens(60, 70);
    stringTest.updateToken(objects);
  }

  @Test
  public void del() {
    stringTest.deleteToken(TOKENSTR + "1");
  }

}

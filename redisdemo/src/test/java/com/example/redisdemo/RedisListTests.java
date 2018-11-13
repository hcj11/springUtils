package com.example.redisdemo;

import com.example.redisdemo.domain.MessgeInfo;
import com.example.redisdemo.redis.ListTest;
import com.example.redisdemo.redis.commonTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisListTests {

  @Autowired
  ListTest listTest;
  @Autowired
  commonTest commontest;
  private final String MessgeInfoSTR = "MessgeInfolist:";
  @Test
  public void demo(){
    listTest.demo();
  }
  @Test
  public void addTime(){
    commontest.addTime();
  }

//  @Test
//  public void select() {
//    System.out.println(listTest.selectMessgeInfo(MessgeInfoSTR + "0"));
//  }

  @Test
  public void selectMessgeInfoWithCollection() {
    Set<String> strings = listTest.selectMessgeInfoListWithPartten(MessgeInfoSTR);
    List<List<MessgeInfo>> MessgeInfos = listTest.selectMessgeInfoWithCollection(strings);
    for (List<MessgeInfo> messgeInfos:MessgeInfos){
      for (MessgeInfo messgeInfo:messgeInfos){
        System.out.println(messgeInfo);
      }
    }
  }

  @Test
  public void deleteCollections() {
    Set<String> strings = listTest.selectMessgeInfoListWithPartten(MessgeInfoSTR);
    listTest.deleteMessgeInfos(strings);
  }

  @Test
  public void selectMessgeInfoListWithPartten() {
    System.out.println(listTest.selectMessgeInfoListWithPartten(MessgeInfoSTR));
  }

  @Test
  public void addMessgeInfoWithPipeline(){
    ArrayList<MessgeInfo> objects = getMessgeInfos(50, 60);
    listTest.addMessageWithPipeline(objects);
  }


  private ArrayList<MessgeInfo> getMessgeInfos(int i2, int i3) {
    ArrayList<MessgeInfo> objects = new ArrayList<>();
    for (int i = i2; i < i3; i++) {
      MessgeInfo MessgeInfoManageTest = new MessgeInfo();
      MessgeInfoManageTest.setId((long) i);
      MessgeInfoManageTest.setMessage("随机消息： "+i);
      objects.add(MessgeInfoManageTest);
    }
    return objects;
  }

  @Test
  public void update() {

    ArrayList<MessgeInfo> objects = getMessgeInfos(60, 70);
    listTest.updateMessgeInfo(objects);
  }

  @Test
  public void del() {
    listTest.deleteMessgeInfo(MessgeInfoSTR + "1");
  }

}

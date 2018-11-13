package com.example.redisdemo;

import com.example.redisdemo.domain.MessgeInfo;
import com.example.redisdemo.redis.SetTest;
import com.example.redisdemo.redis.commonTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisSetTests {

  @Autowired
  SetTest setTest;
  @Autowired
  commonTest commontest;

  private final String MessgeInfoSTR = "MessgeInfoset:";
  @Test
  public void demo(){

    setTest.demo();
  }
  @Test
  public void add(){
    setTest.add();
  }
  @Test
  public void addTime(){
    commontest.addTime();
  }

  @Test
  public void selectMessgeInfoWithCollection() {
    Set<String> strings = setTest.selectMessgeInfoListWithPartten(MessgeInfoSTR);
    List<List<MessgeInfo>> MessgeInfos = setTest.selectMessgeInfoWithCollection(strings);
    for (List<MessgeInfo> messgeInfos:MessgeInfos){
      for (MessgeInfo messgeInfo:messgeInfos){
        System.out.println(messgeInfo);
      }
    }
  }

  @Test
  public void deleteCollections() {
    Set<String> strings = setTest.selectMessgeInfoListWithPartten(MessgeInfoSTR);
    setTest.deleteMessgeInfos(strings);
  }

  @Test
  public void selectMessgeInfoListWithPartten() {
    System.out.println(setTest.selectMessgeInfoListWithPartten(MessgeInfoSTR));
  }

  @Test
  public void addMessgeInfoWithPipeline(){
    ArrayList<MessgeInfo> objects = getMessgeInfos(50, 60);
    setTest.addMessageWithPipeline(objects);
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
    setTest.updateMessgeInfo(objects);
  }

  @Test
  public void del() {
    setTest.deleteMessgeInfo(MessgeInfoSTR + "1");
  }

}

package com.example.oauth2;

import com.example.oauth2.domain.Token;
import com.example.oauth2.redis.TokenManageTest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by hcj on 18-7-25
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class RedisStringDemo {

  @Test
  public void serialObject() throws IOException {
    String dir="/home/hcj/backup/";
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(dir,"aa.txt"
    )));
    Token tokenManageTest = new Token();
    List<String> otherList =
        tokenManageTest.getOtherList();
    otherList.add("1");
    Map<String, Object> otherMap = tokenManageTest.getOtherMap();
    otherMap.put("1","2");
    objectOutputStream.writeObject(tokenManageTest);
  }

  @Test
  public void derialObject() throws IOException, ClassNotFoundException {
    String dir="/home/hcj/backup/";
    ObjectInputStream objectInputStream = new ObjectInputStream(
        new FileInputStream(new File(dir, "aa.txt"
        )));
    Token token = (Token) objectInputStream.readObject();
    System.out.println(token);

  }

}

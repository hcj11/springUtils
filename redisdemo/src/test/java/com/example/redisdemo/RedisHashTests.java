package com.example.redisdemo;

import com.example.redisdemo.domain.ArticleInfo;
import com.example.redisdemo.redis.HashTest;
import com.example.redisdemo.redis.commonTest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisHashTests {

  @Autowired
  HashTest hashTest;
  @Autowired
  commonTest commontest;

  private final String ArticleInfoSTR = "ArticleInfohash:";

  @Test
  public void demo() {

    hashTest.demo();
  }

  @Test
  public void addTime() {
    commontest.addTime();
  }

  @Test
  public void selectArticleInfoWithCollection() {
    // 多次获取key
    Set<String> strings = hashTest.selectArticleInfoListWithPartten(ArticleInfoSTR);
    LinkedList<String> linkedList = new LinkedList<String>(strings);
    List<ArticleInfo> ArticleInfos = hashTest.selectArticleInfoWithCollection(
        linkedList.get(0));
      for (ArticleInfo ArticleInfo : ArticleInfos) {
        System.out.println(ArticleInfo);
    }
  }

  @Test
  public void deleteCollections() {
    Set<String> strings = hashTest.selectArticleInfoListWithPartten(ArticleInfoSTR);
    hashTest.deleteArticleInfos(strings);
  }

  @Test
  public void selectArticleInfoListWithPartten() {
    System.out.println(hashTest.selectArticleInfoListWithPartten(ArticleInfoSTR));
  }

  @Test
  public void addArticleInfoWithPipeline() {
    ArrayList<ArticleInfo> objects = getArticleInfos(50, 60);
    hashTest.addMessageWithPipeline(objects);
  }


  private ArrayList<ArticleInfo> getArticleInfos(int i2, int i3) {
    ArrayList<ArticleInfo> objects = new ArrayList<>();
    for (int i = i2; i < i3; i++) {
      ArticleInfo ArticleInfoManageTest = new ArticleInfo();
      ArticleInfoManageTest.setArticleUser("发表人： "+i + "");
      ArticleInfoManageTest.setContent("随机消息： " + i);
      objects.add(ArticleInfoManageTest);
    }
    return objects;
  }

  @Test
  public void update() {

    ArrayList<ArticleInfo> objects = getArticleInfos(60, 70);
    hashTest.updateArticleInfo(objects);
  }

  @Test
  public void del() {
    hashTest.deleteArticleInfo(ArticleInfoSTR + "1");
  }

}

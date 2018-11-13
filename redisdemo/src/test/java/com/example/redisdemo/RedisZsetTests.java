package com.example.redisdemo;

import static com.example.redisdemo.redis.ZsetTest.stringRedisSerializer;

import com.example.redisdemo.redis.ZsetTest;
import com.example.redisdemo.redis.commonTest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.connection.DefaultStringTuple;
import org.springframework.data.redis.connection.DefaultTuple;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisZsetTests {

  @Autowired
  ZsetTest zsetTest;
  @Autowired
  commonTest commontest;

  private final String StringSTR = "Stringzset:";
  private final ExecutorService service = Executors.newFixedThreadPool(10);


  @Test
  public void demo() {
    zsetTest.demo();
  }

  public static void main(String[] args) {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
        RedisdemoApplication.class);
    RedisZsetTests redisZsetTests = new RedisZsetTests();
    redisZsetTests.test(context);
  }

  //  @Test
  public void test(AnnotationConfigApplicationContext context) {
    ZsetTest zsetTest = context.getBean("zsetTest", ZsetTest.class);
    for (int i = 0; i < 10; i++) {
      service.submit(new Runnable() {
        @Override
        public void run() {
          zsetTest.addTestTx();
        }
      });
    }
    service.shutdown();
    try {
      service.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    context.destroy();;

  }

  @Test
  public void aggregate() {
    zsetTest.aggregate();

  }

  @Test
  public void addTime() {
    commontest.addTime();
  }

  @Test
  public void selectStringWithCollection() {
    // 多次获取key
    Set<String> strings = zsetTest.selectStringListWithPartten(StringSTR);
    LinkedList<String> linkedList = new LinkedList<String>(strings);
    HashMap<String, Double> map = zsetTest.selectStringWithCollection(linkedList.get(0));
    Set<Entry<String, Double>> entries = map.entrySet();
    for (Entry<String, Double> entry : entries) {
      System.out.println("用户: " + entry.getKey() + ", 分数： " + entry.getValue());
    }
  }

  @Test
  public void deleteCollections() {
    Set<String> strings = zsetTest.selectStringListWithPartten(StringSTR);
    zsetTest.deleteStrings(strings);
  }

  @Test
  public void selectStringListWithPartten() {
    System.out.println(zsetTest.selectStringListWithPartten(StringSTR));
  }

  @Test
  public void addStringWithPipeline() {
    Set<Tuple> tuples = getStrings(50, 60);
    zsetTest.addMessageWithPipeline(tuples);
  }


  private Set<Tuple> getStrings(int i2, int i3) {
    Set<Tuple> objects = new HashSet<>();
    for (int i = i2; i < i3; i++) {
      byte[] serialize = stringRedisSerializer.serialize("articleUser-" + i);
      Tuple tuple = new DefaultStringTuple(new DefaultTuple(serialize, (double) i),
          StringSTR + i);
      objects.add(tuple);
    }
    return objects;
  }

  @Test
  public void update() {

    zsetTest.updateString();
  }

  @Test
  public void del() {
    zsetTest.deleteString(StringSTR + "1");
  }

}

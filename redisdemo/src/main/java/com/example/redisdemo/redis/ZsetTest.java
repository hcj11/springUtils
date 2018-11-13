package com.example.redisdemo.redis;

import com.example.redisdemo.util.RandomUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands.Aggregate;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;

/**
 * byte[] <-> string Created by hcj on 18-7-25
 */
@Configuration
public class ZsetTest implements InitializingBean {

  private final String StringSTR = "Stringzset:";
  private final int timeout = 60 * 60 * 24 * 30;

  @Autowired
  @Qualifier("redisTemplateZ")
  private RedisTemplate<String, Object> redisTemplateZ;

  public final static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
  private final static JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
  private ZSetOperations<String, Object> zSetOperations;

  public void afterPropertiesSet() throws Exception {
    // bean以实例后， 并进行了属性填充
    zSetOperations = redisTemplateZ.opsForZSet();
  }




  public void addTestTx() {

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // 未开启管道， 导致结果重叠， 不过最终的结果是对的， 多个命令只是重叠，
    // 开启管道 , 则可以保证事务性
//    RedisCallback<List<Object>> redisCallback = new RedisCallback<List<Object>>() {
//      @Override
//      public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
//        byte[] serialize = stringRedisSerializer.serialize(StringSTR + "14");
//        byte[] serialize1 = stringRedisSerializer.serialize("articleUser-9");
//        // 观察某个key是否有调整 .若该键达到了25,那么 就不要进行增加
////        connection.openPipeline();
//        connection.watch(serialize);
//        if (25d == connection.zScore(serialize, serialize1)) {
//          connection.unwatch();
//          System.out.println("该键达到了25,那么 就不要进行增加");
//        }
//        connection.multi();
//        connection.zIncrBy(serialize, 1, serialize1);
//        return connection.exec();
//      }
//    };
    // 使用同一根connection进行的操作， 开启事务保证有效， 乐观锁，
    SessionCallback<List<Object>> sessionCallback = new SessionCallback<List<Object>>() {
      @Override
      public List<Object> execute(RedisOperations operations) throws DataAccessException {

        operations.watch(StringSTR + "14");
        Double score = operations.opsForZSet().score(StringSTR + "14", "articleUser-9");
        if(score > 90){
          operations.unwatch();
          System.out.println("该键达到了55,那么 就不要进行增加");
          // 结束增加操作.
          return null;
        }
        operations.multi();
  // 每个线程尝试增加到50 那么只有一个线程执行成功，其他线程的执行都会失败
        operations.opsForZSet()
            .incrementScore(StringSTR + "14", "articleUser-9", 12);
        operations.opsForZSet()
            .incrementScore(StringSTR + "15", "articleUser-3", 1);


        return operations.exec();
      }
    };

    List<Object> execute = redisTemplateZ.execute(sessionCallback);
    if(execute.size()!=0){
      System.out.println("结果： " + execute.get(0).toString());
    }else{
      System.out.println("完成");
    }

//    Double aDouble = zSetOperations.incrementScore(StringSTR + "14", "articleUser-9", 1);
//    System.out.println("结果： "+aDouble); // 如果开启事务后，应该是10

//    zSetOperations.incrementScore(StringSTR+"14","articleUser-9",-1);

  }

  public void addMessageWithPipeline(Set<Tuple> tuples) {
    String s = StringSTR + RandomUtil.generateActivationKey();
    byte[] serialize = stringRedisSerializer.serialize(s);

    RedisCallback<List<Object>> redisCallback = new RedisCallback<List<Object>>() {

      public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
        connection.openPipeline();
        connection.zAdd(serialize, tuples);
        return connection.closePipeline();
      }
    };
    List<Object> execute = redisTemplateZ.execute(redisCallback, false, true);
    for (Object object : execute) {
      System.out.println(object.toString());
    }
  }

  //    public List<String> selectStringWithCollection(Collection<String> keys) {
//    List<Object> objects1 = new ArrayList<>();
//    for (String key:keys){
//      Set<Object> keys1 = zSetOperations.keys(key);
//      objects1.add(keys1);
//    }
//    List<Object> objects3 = new LinkedList<>(objects1);
//
//
//    // 获取某个key的数据，开管道 对应已经提供多数据的get 和set 无需开管道
//    RedisCallback<Object> redisCallback = new RedisCallback<Object>() {
//      @Override
//      public Object doInRedis(RedisConnection connection) throws DataAccessException {
//        connection.openPipeline();
//        for (String key:keys){
//          byte[] serialize = stringRedisSerializer.serialize(key);
//          connection.zSetCommands().zre
////          connection.hashCommands().hMGet(serialize,entries);
//
//        }
//
//        return connection.closePipeline();
//      }
//    };
//
//    // desriable List<byte[]>
//    List<Object> execute = ( List<Object>) redisTemplateZ.execute(redisCallback, false, true);
//    List<List<MessgeInfo>> objects2 = new ArrayList<>();
//    List<MessgeInfo> objects = new ArrayList<>();
//    for (Object object : execute) {
//      List<Object> object1 = (List<Object>) object;
//      for (Object objectTmp:object1){
//        MessgeInfo deserialize = (MessgeInfo) jdkSerializationRedisSerializer.deserialize((byte[]) objectTmp);
//        objects.add(deserialize);
//      }
//      objects2.add(objects);
//    }
//    return objects2;
//  }
  // 根据key查询所有value
  public HashMap<String, Double> selectStringWithCollection(String key) {
    // 多个key的查询，那最好使用管道， 单个key则直接两个命令 按照set顺序返回结果
    Set<TypedTuple<Object>> typedTuples = zSetOperations.rangeWithScores(key, 0L, 5L);

    List<Tuple> objects = new ArrayList<>();
    HashMap<String, Double> map = new HashMap<>();

    for (TypedTuple<Object> objectTmp : typedTuples) {
      String value = (String) objectTmp.getValue();
      Double score = objectTmp.getScore();
      map.put(value, score);
    }
    return map;
  }


  public Set<String> selectStringListWithPartten(String key) {
    // todo 获取对应表达式的所有的key
    Set<String> keys = zSetOperations.getOperations().keys(key + "*");
    return keys;
  }

  public void updateString() {
    // 通常会直接传送map
    String key = "Stringzset:60466092001475982081";
    // 按照排名进行移除， 6 包括5
    zSetOperations.removeRange(key, 0, 5);
  }


  public void demo() {
    String key = "Stringzset:14";
    String key2 = "Stringzset:15";
    String interstore = "Stringzset:interstore";
    String unionstore = "Stringzset:unionstore";

    for (int i = 0; i < 10; i++) {
      // 已存在的value会插入失败
      zSetOperations.add(key, "articleUser-" + i, (double) i);
//      System.out.println("插入状态： "+add);
      zSetOperations.add(key2, "articleUser-" + (i + 3), (double) i);
    }
//    Assert.isTrue(zSetOperations.size(key)==10,"");
//    Assert.isTrue(zSetOperations.size(key2)==10,"");
//
    Double aDouble = zSetOperations.incrementScore(key, "articleUser-0", 100);
    Assert.isTrue(aDouble == 100L, "");
//    Long count = zSetOperations.count(key, 1, 3);
//    Assert.isTrue(count==3,"");
//    // 根据分值
//    Set<Object> range = zSetOperations.range(key, 3, 6);
//    Assert.isTrue(range.size()==4,"");
//    System.out.println(range);
//    // 获取排名 第一位是0.
//    Long rank = zSetOperations.rank(key, "articleUser-0");
//    Assert.isTrue(rank==9L,"该用户的排名： "+rank);
//    // 根据分值获取 包含最大值和最小值
//    Set<Object> objects = zSetOperations.rangeByScore(key, 4, 5);
//    Assert.isTrue(objects.size()==2,"");
//    System.out.println(objects);
    // 按照分值从大到小排列
    Long aLong = zSetOperations.reverseRank(key, "articleUser-0");
    Assert.isTrue(aLong == 0, "");
    // 按照分值由高打低排序，   并获取排名0-4 "articleUser-0" -> 0
    Set<Object> objects = zSetOperations.reverseRange(key, 0, 4);
    System.out.println(objects);
    // 默认按照分值顺序由低到高 ，那么翻转的话就是按照分值由高到底。   按照分值获取数据
    Set<Object> objects1 = zSetOperations.reverseRangeByScore(key, 0, 4);
    System.out.println(objects1);
    // 分页展示？ yes
    Set<TypedTuple<Object>> typedTuples = zSetOperations
        .reverseRangeByScoreWithScores(key, 0, 4, 1, 2);
    System.out.println(typedTuples);

    // 按分数删除,返回
    Long aLong1 = zSetOperations.removeRangeByScore(key, 0, 4);
    Assert.isTrue(aLong1 == 4L, "");
    // 按排名删除
    Long aLong2 = zSetOperations.removeRange(key, 0, 3);
    Assert.isTrue(aLong2 == 4L, "");
    // 按照值删除
    zSetOperations.remove(key, "articleUser-0");
    Long size = zSetOperations.size(key);
    Assert.isTrue(size == 1, "无数据");

    // 交集和并集
    // zset and zset 进行交集 默认使用聚合函数， sum ,并按照value进行的聚合
    Long aLong3 = zSetOperations.intersectAndStore(key, key2, interstore);
    Assert.isTrue(aLong3 == 1, "");
    Set<Object> range = zSetOperations.range(interstore, 0, 1);
    System.out.println(range);

    Long aLong4 = zSetOperations.unionAndStore(key, key2, unionstore);
    Assert.isTrue(aLong4 == 10, "");
    // -1 最后
    Set<Object> range1 = zSetOperations.range(unionstore, 0, -1);
    System.out.println(range1);

    // 和 set 进行 求交并集 原生 excute

  }

  public void aggregate() {
    String key = "Stringzset:14";
    String key2 = "Stringzset:15";
    String interstore = "Stringzset:interstore";
    String unionstore = "Stringzset:unionstore";
    String set = "articleInfoset:001";
    int[] weights = new int[3];
    weights[0] = 1;
    weights[1] = 1;
    weights[2] = 1;

    RedisCallback<List<Object>> redisCallback = new RedisCallback<List<Object>>() {
      @Override
      public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
        connection.openPipeline();
        byte[] serialize = stringRedisSerializer.serialize(key);
        byte[] serialize1 = stringRedisSerializer.serialize(key2);
        byte[] serialize2 = stringRedisSerializer.serialize(set);
        byte[] serialize3 = stringRedisSerializer.serialize(interstore);
        byte[] serialize4 = stringRedisSerializer.serialize(unionstore);
        connection.zSetCommands()
            .zInterStore(serialize3, Aggregate.SUM, weights, serialize, serialize1, serialize2);

        connection.zSetCommands()
            .zUnionStore(serialize4, Aggregate.SUM, weights, serialize, serialize1, serialize2);

        return connection.closePipeline();
      }
    };
    List<Object> execute = redisTemplateZ.execute(redisCallback, false, true);

    System.out.println(execute);
  }


  public void deleteStrings(Collection<String> keys) {
    // 若删除某一些key的话可以用管道
//    zSetOperations.remove();
//    new RedisCallback<>() {
//      @Override
//      public Object doInRedis(RedisConnection connection) throws DataAccessException {
//    // 删除值为。。 的数量，
//        connection.listCommands().lRem()
//        return null;
//      }
//    }
//    Long delete = zSetOperations.getOperations().delete(keys);
//    System.out.println("删除成功：" + delete);
  }


  public void deleteString(String id) {
    Boolean delete = zSetOperations.getOperations().delete(id);
    if (delete) {
      System.out.println("删除成功：" + id);
    }
  }

}

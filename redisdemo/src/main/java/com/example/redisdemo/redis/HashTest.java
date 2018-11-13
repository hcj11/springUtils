package com.example.redisdemo.redis;

import com.example.redisdemo.domain.ArticleInfo;
import com.example.redisdemo.domain.MessgeInfo;
import com.example.redisdemo.util.RandomUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;
import sun.awt.image.ImageWatched.Link;

/**
 * byte[] <-> string Created by hcj on 18-7-25
 */
@Configuration
public class HashTest implements InitializingBean {

  private final String ArticleInfoSTR = "ArticleInfohash:";
  private final int timeout = 60 * 60 * 24 * 30;

  @Autowired
  @Qualifier("redisTemplate")
  private RedisTemplate<String, Object> redisTemplate;
  @Autowired
  @Qualifier("redisTemplateX")
  private RedisTemplate<String, Object> redisTemplateX;
  @Autowired
  @Qualifier("redisTemplateY")
  private RedisTemplate<String, Object> redisTemplateY;

  private final static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
  private final static JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
  private HashOperations<String, Object, Object> hashOperations;
  private HashOperations<String, Object, Object> hashOperationsX;
  private HashOperations<String, Object, Object> hashOperationsY;


  public void afterPropertiesSet() throws Exception {
    // bean以实例话， 并进行了属性填充
    hashOperations = redisTemplate.opsForHash();
    hashOperationsX = redisTemplateX.opsForHash();
    hashOperationsY = redisTemplateY.opsForHash();


  }

  public void addMessageWithPipeline(List<ArticleInfo> ArticleInfos) {
    String s = ArticleInfoSTR + RandomUtil.generateActivationKey();
    // ArticleInfolist:1111
    byte[] serialize = stringRedisSerializer.serialize(s);

    RedisCallback<List<Object>> redisCallback = new RedisCallback<List<Object>>() {

      public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
        connection.openPipeline();
        HashMap<byte[], byte[]> map = new HashMap<>();
        for (ArticleInfo ArticleInfo : ArticleInfos) {
          byte[] serialize1 = jdkSerializationRedisSerializer.serialize(ArticleInfo);
          // add map
          map.put(jdkSerializationRedisSerializer.serialize(RandomUtil.generateTenActivationKey()),
              serialize1);
        }
        connection.hMSet(serialize, map);
        return connection.closePipeline();
      }
    };
    List<Object> execute = redisTemplate.execute(redisCallback, false, true);
    for (Object object : execute) {
      System.out.println(object.toString());
    }
  }
//  public List<ArticleInfo> selectArticleInfoWithCollection(Collection<String> keys) {
//    List<Object> objects1 = new ArrayList<>();
//    for (String key:keys){
//      Set<Object> keys1 = hashOperations.keys(key);
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
//          objects3.
//          connection.hashCommands().hMGet(serialize,entries);
//        }
//
//        return connection.closePipeline();
//      }
//    };
//
//    // desriable List<byte[]>
//    List<Object> execute = ( List<Object>) redisTemplate.execute(redisCallback, false, true);
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
  public List<ArticleInfo> selectArticleInfoWithCollection(String key) {
    // 多个key的查询，那最好使用管道， 单个key则直接两个命令
    List<Object> values = hashOperations.values(key);
    List<ArticleInfo> objects = new ArrayList<>();
    for (Object objectTmp : values) {
      objects.add((ArticleInfo) objectTmp);
    }
    return objects;
  }



  public Set<String> selectArticleInfoListWithPartten(String key) {
    // todo 获取对应表达式的所有的key
    Set<String> keys = hashOperations.getOperations().keys(key + "*");
//    System.out.println(keys);
    return keys;
  }

  public void updateArticleInfo(List<ArticleInfo> ArticleInfos) {
    // 通常会直接传送map
    String key="ArticleInfohash:02131457538839954317";
    Set<Object> keys = hashOperations.keys(key);
    List<Object> objects = new ArrayList<>(keys);
    List<Object> objects1 = objects.subList(0, 5);
    List<String> collect = objects1.stream().map(object -> {
      return (String) object;
    }).collect(Collectors.toList());

    LinkedList<ArticleInfo> articleInfos = new LinkedList<>(ArticleInfos);
    hashOperations.delete(key,collect);
    HashMap<Object, Object> map = new HashMap<>();

    for (String object:collect){
      map.put(object,articleInfos.pop());
    }
    hashOperations.putAll(key,map);
  }

  // 修剪长度
  public void demo() {
    String key =  "ArticleInfohash:14";
    String key2 = "ArticleInfohash:15";
    // 返回列表中索引i 对应的 元素 通过string类型进行增减，
    HashMap<String,String> map = new HashMap<>();
    map.put("hcj","1");
    map.put("hah","2");
    hashOperationsY.putAll(key,map);
    Assert.isTrue(hashOperationsY.hasKey(key,"hcj"),"");
    Assert.isTrue(!hashOperationsY.hasKey(key,"1hcj"),"");

    Long hcj = hashOperationsY.increment(key, "hcj", 1L);
    Long hah = hashOperationsY.increment(key, "hah", 1L);
    Assert.isTrue(hcj==2L,"");
    Assert.isTrue(hah==3L,"");

  }


  public void deleteArticleInfos(Collection<String> keys) {
    // 若删除某一些key的话可以用管道
//    hashOperations.remove();
//    new RedisCallback<>() {
//      @Override
//      public Object doInRedis(RedisConnection connection) throws DataAccessException {
//    // 删除值为。。 的数量，
//        connection.listCommands().lRem()
//        return null;
//      }
//    }
//    Long delete = hashOperations.getOperations().delete(keys);
//    System.out.println("删除成功：" + delete);
  }


  public void deleteArticleInfo(String id) {
    Boolean delete = hashOperations.getOperations().delete(id);
    if (delete) {
      System.out.println("删除成功：" + id);
    }
  }

}

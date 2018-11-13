package com.example.redisdemo.redis;

import com.example.redisdemo.domain.MessgeInfo;
import com.example.redisdemo.util.RandomUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;

/**
 * byte[] <-> string Created by hcj on 18-7-25
 */
@Configuration
public class SetTest implements InitializingBean {

  private final String MessgeInfoSTR = "MessgeInfoset:";
  private final int timeout = 60 * 60 * 24 * 30;

  @Autowired
  @Qualifier("redisTemplate")
  private RedisTemplate<String, Object> redisTemplate;
  @Autowired
  @Qualifier("redisTemplateZ")
  private RedisTemplate<String, Object> redisTemplateZ;
  private final static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
  private final static JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
  private SetOperations<String, Object> setOperations;

  public void afterPropertiesSet() throws Exception {
    // bean以实例话， 并进行了属性填充
    setOperations =
        redisTemplateZ.opsForSet();
  }


  public void addMessageWithPipeline(List<MessgeInfo> MessgeInfos) {
    String s = MessgeInfoSTR + RandomUtil.generateActivationKey();
    // MessgeInfolist:1111
    byte[] serialize = stringRedisSerializer.serialize(s);

    RedisCallback<List<Object>> redisCallback = new RedisCallback<List<Object>>() {

      public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
        connection.openPipeline();
        for (MessgeInfo messgeInfo : MessgeInfos) {
          byte[] serialize1 = jdkSerializationRedisSerializer.serialize(messgeInfo);
          connection.sAdd(serialize, serialize1);
        }
        return connection.closePipeline();
      }
    };
    List<Object> execute = redisTemplate.execute(redisCallback, false, true);
    for (Object object : execute) {
      System.out.println(object.toString());
    }
  }

  // 根据key查询所有value
  public List<List<MessgeInfo>> selectMessgeInfoWithCollection(Collection<String> keys) {
    // 获取某个key的数据，开管道
    RedisCallback<Object> redisCallback = new RedisCallback<Object>() {
      @Override
      public Object doInRedis(RedisConnection connection) throws DataAccessException {
        connection.openPipeline();
        for (String key : keys) {
          byte[] serialize = stringRedisSerializer.serialize(key);
          // todo 貌似获取不到 0 ,-1
          connection.setCommands().sMembers(serialize);
        }
        return connection.closePipeline();
      }
    };
    // desriable
    List<Object> execute = (List<Object>) redisTemplate.execute(redisCallback, false, true);
    List<List<MessgeInfo>> objects2 = new ArrayList<>();
    List<MessgeInfo> objects = new ArrayList<>();
    for (Object object : execute) {
      Set<Object> object1 = (Set<Object>) object;
      for (Object objectTmp : object1) {
        MessgeInfo deserialize = (MessgeInfo) jdkSerializationRedisSerializer
            .deserialize((byte[]) objectTmp);
        objects.add(deserialize);
      }
      objects2.add(objects);
    }
    return objects2;
  }

  public Set<String> selectMessgeInfoListWithPartten(String key) {
    // todo 获取对应表达式的所有的key
    Set<String> keys = setOperations.getOperations().keys(key + "*");
//    System.out.println(keys);
    return keys;
  }

  public void updateMessgeInfo(List<MessgeInfo> MessgeInfos) {
    // 未开启事务，当多个客户端同时访问时， size的大小就不固定了

    // 若有对应关系就是map
    Set<String> strings = selectMessgeInfoListWithPartten(MessgeInfoSTR);

    // 对一个key进行替换，
    LinkedList<String> strings1 = new LinkedList<>(strings);
    Long size = setOperations.size(strings1.get(0));

    // todo 不过期
    RedisCallback<List<Object>> redisCallback = new RedisCallback<List<Object>>() {
      public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
        int count = 0;
        connection.openPipeline();
        String pop = strings1.pop();
        byte[] serialize = stringRedisSerializer.serialize(pop);
        for (int i = 0; i < size; i++) {
          // spop Redis 3.2引入了一个可选count参数，可以传递给 SPOP 以便在一次调用中检索多个元素。
          connection.setCommands().sPop(serialize);
        }

        for (MessgeInfo messgeInfo : MessgeInfos) {
          ++count;
          byte[] serialize1 = jdkSerializationRedisSerializer.serialize(messgeInfo);
          // 更新操作，先删除，后创建, 不适合更新，
          connection.setCommands().sAdd(serialize, serialize1);
        }
        return connection.closePipeline();
      }
    };
    List<Object> execute = redisTemplate.execute(redisCallback, false, true);
    for (Object object : execute) {
      System.out.println(object.toString());
    }
  }

  public void add() {
    String key = "articleInfoset:001";
    // 必须指明大小。
    Object[] objects = new Object[3];
    for (int i = 9; i < 12; i++) {
      objects[i - 9] = "articleUser-" + i;
    }

    setOperations.add(key, objects);
  }

  // 修剪长度
  public void demo() {
    String key = "MessgeInfoset:14";
    String key2 = "MessgeInfoset:15";
    // 返回列表中索引i 对应的 元素
    System.out.println(setOperations.add(key, "123"));
    System.out.println(setOperations.add(key, "321"));
    for (int i = 0; i < 10; i++) {
      System.out.println(setOperations.add(key2, i + ""));
    }
    System.out.println(setOperations.move(key, "123", key2));
    //  组合，关联
    Set<Object> members = setOperations.members(key);
    Set<Object> members2 = setOperations.members(key2);
    setOperations.unionAndStore(key, key2, key);
    Set<Object> difference = setOperations.difference(key, key2);
    Assert.isTrue(members.size() == difference.size(), "");
    Set<Object> intersect = setOperations.intersect(key, key2);
    Assert.isTrue(intersect.size() == members2.size(), "");


  }


  public void deleteMessgeInfos(Collection<String> keys) {
    // 若删除某一些key的话可以用管道
//    setOperations.remove();
//    new RedisCallback<>() {
//      @Override
//      public Object doInRedis(RedisConnection connection) throws DataAccessException {
//    // 删除值为。。 的数量，
//        connection.listCommands().lRem()
//        return null;
//      }
//    }
//    Long delete = setOperations.getOperations().delete(keys);
//    System.out.println("删除成功：" + delete);
  }


  public void deleteMessgeInfo(String id) {
    Boolean delete = setOperations.getOperations().delete(id);
    if (delete) {
      System.out.println("删除成功：" + id);
    }
  }

}

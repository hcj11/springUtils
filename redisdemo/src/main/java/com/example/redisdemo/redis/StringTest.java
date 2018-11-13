package com.example.redisdemo.redis;

import static java.util.concurrent.TimeUnit.SECONDS;

import com.example.redisdemo.domain.Token;
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
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * byte[] <-> string Created by hcj on 18-7-25
 */
@Configuration
public class StringTest implements TokenManageTest, InitializingBean {

  private final String TOKENSTR = "tokenstr:";
  private final int timeout = 60 * 60 * 24 * 30;

  @Autowired
  @Qualifier("redisTemplate")
  private RedisTemplate<String, Object> redisTemplate;

  @Autowired
  @Qualifier("redisTemplateX")
  private RedisTemplate<String, Object> redisTemplateX;
  @Autowired
  @Qualifier("redisTemplateW")
  private RedisTemplate<String, String> redisTemplateW;


  public ValueOperations<String, Object> getValueOperations() {
    return valueOperations;
  }

  public RedisTemplate<String, String> getredisTemplateW() {
    return redisTemplateW;
  }

  private final static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
  private final static JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();

  private ValueOperations<String, Object> valueOperations;
  private ValueOperations<String, Object> stringObjectValueOperations;
  private ValueOperations<String, String> stringStringValueOperations;
//  private ValueOperations<String, Object> valueOperationsZ;

//  public ValueOperations<String, Object> getValueOperationsZ() {
//    return valueOperationsZ;
//  }

  @Override
  public void afterPropertiesSet() throws Exception {
    // bean以实例话， 并进行了属性填充
    // factory 开启管道

     valueOperations = redisTemplate.opsForValue();
     stringObjectValueOperations = redisTemplateX.opsForValue();
     stringStringValueOperations = redisTemplateW.opsForValue();
  }


  public void addTokenWithPipeline(List<Token> tokens) {
    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();

    RedisCallback<List<Object>> redisCallback = new RedisCallback<List<Object>>() {
      int count = 0;

      @Override
      public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
        connection.openPipeline();
        ++count;
        for (Token token : tokens) {
          // 序列化？
          String s = TOKENSTR + RandomUtil.generateActivationKey();
          byte[] serialize = stringRedisSerializer.serialize(s);
          byte[] serialize1 = jdkSerializationRedisSerializer.serialize(token);
          connection.set(serialize, serialize1, Expiration.seconds(1000), SetOption.UPSERT);
        }
        return connection.closePipeline();
      }
    };
    List<Object> execute = redisTemplate.execute(redisCallback, false, true);
    for (Object object : execute) {
      System.out.println(object.toString());
    }

  }

  @Override
  public void updateToken(List<Token> tokens) {
    // 若有对应关系就是map
    Set<String> strings = selectTokenListWithPartten(TOKENSTR);
    LinkedList<String> strings1 = new LinkedList<>(strings);

    // todo 不过期
    RedisCallback<List<Object>> redisCallback = new RedisCallback<List<Object>>() {
      @Override
      public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
        connection.openPipeline();
        for (Token token : tokens) {
          // 序列化？
          String pop = strings1.pop();
          byte[] serialize = stringRedisSerializer.serialize(pop);
          byte[] serialize1 = jdkSerializationRedisSerializer.serialize(token);
          connection.getSet(serialize, serialize1);
        }
        return connection.closePipeline();
      }
    };
    List<Object> execute = redisTemplate.execute(redisCallback, false, true);
    for (Object object : execute) {
      System.out.println(object.toString());
    }
  }


  @Override
  public void addToken(List<Token> tokens) {
    for (Token token : tokens) {
      String s = RandomUtil.generateActivationKey();
      valueOperations.set(TOKENSTR + s, token, timeout, SECONDS);
    }
  }

  @Override
  public Token selectToken(String key) {
    return (Token) valueOperations.get(key);
  }

  public List<Token> selectTokenWithCollection(Collection<String> key) {
    // 开流水线，未开事务，
    List<Object> objects = stringObjectValueOperations.multiGet(key);
    List<Token> objects1 = new ArrayList<>();
    for (Object object : objects) {
      objects1.add((Token) object);
    }
    return objects1;
  }

  @Override
  public Set<String> selectTokenListWithPartten(String key) {
    // todo 获取对应表达式的所有的key
    Set<String> keys = valueOperations.getOperations().keys(key + "*");
    System.out.println(keys);
    return keys;
  }

  public void deleteTokens(Collection<String> keys) {
    Long delete = valueOperations.getOperations().delete(keys);
    System.out.println("删除成功：" + delete);
  }

  @Override
  public void deleteToken(String id) {
    Boolean delete = valueOperations.getOperations().delete(id);
    if (delete) {
      System.out.println("删除成功：" + id);
    }
  }


}

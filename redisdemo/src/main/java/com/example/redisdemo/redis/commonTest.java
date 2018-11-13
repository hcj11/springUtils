
package com.example.redisdemo.redis;

import static java.util.concurrent.TimeUnit.SECONDS;

import com.example.redisdemo.domain.Token;
import com.example.redisdemo.util.RandomUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
public class commonTest implements  InitializingBean {

  private final String TOKENSTR = "tokenstr:";
  private final int timeout = 60 * 60 * 24 * 30;

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;
  private ValueOperations<String, Object> valueOperations;
  @Autowired
  StringTest stringTest;
  private  final static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
  private final static JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();

  @Override
  public void afterPropertiesSet() throws Exception {
    valueOperations = redisTemplate.opsForValue();
  }

  // 获取所有
  public void addTime(){
    Set<String> strings = stringTest.selectTokenListWithPartten(TOKENSTR);
    RedisCallback redisCallback = new RedisCallback() {
      @Override
      public Object doInRedis(RedisConnection connection) throws DataAccessException {
        connection.openPipeline();
        for (String ss:strings){
          byte[] serialize = stringRedisSerializer.serialize(ss);
          connection.expire(serialize,1000);
        }
        return connection.closePipeline();
      }
    };
    List<Boolean> execute = (List<Boolean>) redisTemplate.execute(redisCallback, false, true);
    System.out.println(execute);
  }


}

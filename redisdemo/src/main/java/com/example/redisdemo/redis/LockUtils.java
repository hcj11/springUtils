package com.example.redisdemo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

/**
 * redis 分布式锁， Created by hcj on 18-7-27
 */
@Configuration
public class LockUtils {

  // 不同机器的客户端  来访问redis
  // 多个客户端在统一时间内来设置过期时间

  @Autowired
  StringTest stringTest;

  public ValueOperations<String, String> operations() {
    return (ValueOperations<String, String>) stringTest.getredisTemplateW().opsForValue();
  }


  private final static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
  private final static JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();

  public boolean acquire_lock_with_timeout(String key, String value) {
    ValueOperations<String, String> operations = operations();
    // 已经锁住
    if (operations.setIfAbsent(key, value)) {
      return true;
    }
    String currentValue = operations.get(key);

    if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System
        .currentTimeMillis()) {
      // 锁已过期
      String andSet = operations.getAndSet(key, value);
      if (!StringUtils.isEmpty(andSet) && andSet.equals(currentValue)) {
        return true;
      }
    }
    return false;
  }

  public boolean releaseLock(String key, String value) {
    ValueOperations<String, String> operations = operations();
    String s = operations.get(key);
    if (!StringUtils.isEmpty(s) && s.equals(value)) {
      return operations.getOperations().delete(key);
    }
    // 被人为删除,
    return false;
  }

//  public void  test(){
//    ValueOperations<String,String> operations = operations();
//    new RedisCallback<>() {
//      @Override
//      public Object doInRedis(RedisConnection connection) throws DataAccessException {
//        connection.setNX()
//        return null;
//      }
//    }



}

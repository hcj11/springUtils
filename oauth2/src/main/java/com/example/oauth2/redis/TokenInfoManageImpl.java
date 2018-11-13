package com.example.oauth2.redis;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

/**
 * Created by hcj on 18-7-25
 */
@Repository
public class TokenInfoManageImpl implements TokenInfoManage, InitializingBean {
  private final String ACCESSTOKEN="access:";
  private final int timeout = 60 * 60 * 24 * 30;
  @Autowired
  private RedisTemplate<String, Object> redisTemplate;
  private ValueOperations<String, Object> valueOperations;


  @Override
  public int addTime() {
    RedisOperations<String, Object> operations =
        valueOperations.getOperations();
    Set<String> keys = operations.keys(ACCESSTOKEN + "*");
    int count=0;
    for(String key:keys){
      Boolean expire = operations.expire(key, timeout, TimeUnit.SECONDS);
      if(!expire){
        System.out.println("无法设置过期时间,记录.");
        ++count;
      }
    }
    return keys.size()-count;


  }

  @Override
  public void afterPropertiesSet() throws Exception {
    // bean以实例话， 并进行了属性填充
    valueOperations = redisTemplate.opsForValue();
  }
}

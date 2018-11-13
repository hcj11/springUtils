package com.example.oauth2.redis.redistemplate;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * byte[] <-> string Created by hcj on 18-7-25
 */
@Configuration
@Setter
@Getter
public class HashUtil implements InitializingBean {

  @Autowired
  @Qualifier("redisTemplateY")
  private RedisTemplate<String, Object> redisTemplateY;

  private HashOperations<String, Object, Object> hashOperationsY;


  public void afterPropertiesSet() throws Exception {
    // bean以实例话， 并进行了属性填充
    hashOperationsY = redisTemplateY.opsForHash();


  }

}

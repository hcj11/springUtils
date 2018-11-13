package com.example.oauth2.redis.redistemplate;

import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

/**
 * byte[] <-> string Created by hcj on 18-7-25
 */
@Configuration
@Getter
public class ZsetUtil implements InitializingBean {

  @Autowired
  @Qualifier("redisTemplateZ")
  private RedisTemplate<String, Object> redisTemplateZ;

  private ZSetOperations<String, Object> zSetOperations;

  public void afterPropertiesSet() throws Exception {
    // bean以实例后， 并进行了属性填充
    zSetOperations = redisTemplateZ.opsForZSet();
  }


}

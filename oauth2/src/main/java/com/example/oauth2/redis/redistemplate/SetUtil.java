package com.example.oauth2.redis.redistemplate;

import com.example.oauth2.service.util.RandomUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
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
@Setter
@Getter
public class SetUtil implements InitializingBean {

  @Autowired
  @Qualifier("redisTemplateX")
  private RedisTemplate<String, Object> redisTemplate;

  private SetOperations<String, Object> setOperations;
  public void afterPropertiesSet() throws Exception {
    // bean以实例话， 并进行了属性填充
    setOperations =
        redisTemplate.opsForSet();
  }


}

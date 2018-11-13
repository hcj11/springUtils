package com.example.oauth2.serialization;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.security.oauth2.provider.token.store.redis.StandardStringSerializationStrategy;

/**
 * Created by hcj on 18-7-26
 */
@Configuration
public class CustomSerializationStrategy extends StandardStringSerializationStrategy {
  // map 不好转
//  private final static Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
//      Object.class);
  private final static JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();

  @Override
  protected <T> T deserializeInternal(byte[] bytes, Class<T> clazz) {
    return (T) jdkSerializationRedisSerializer.deserialize(bytes);
  }

  @Override
  protected byte[] serializeInternal(Object object) {
    return jdkSerializationRedisSerializer.serialize(object);
  }
}

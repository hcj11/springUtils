package com.example.redisdemo.redis;

import com.example.redisdemo.domain.Token;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class redis {
  @Bean
  public RedisConnectionFactory factory(){
    JedisConnectionFactory factory = new JedisConnectionFactory();
    factory.setDatabase(2);
    return factory;
  }

  @Bean("redisTemplate")
  public RedisTemplate<String,Object> redisTemplate(){
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(factory());
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    return template;
  }

  @Bean("redisTemplateX")
  public RedisTemplate<String,Object> redisTemplateX(){
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(factory());
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new JdkSerializationRedisSerializer());
    return template;
  }
  @Bean("redisTemplateY")
  public RedisTemplate<String,Object> redisTemplateY(){
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(factory());
    template.setKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setHashValueSerializer(new StringRedisSerializer());
    return template;
  }
  @Bean("redisTemplateZ")
  public RedisTemplate<String,Object> redisTemplateZ(){
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(factory());
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new StringRedisSerializer());
    template.setEnableTransactionSupport(true);
    return template;
  }

  @Bean("redisTemplateW")
  public RedisTemplate<String,String> redisTemplateW(){
    RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setConnectionFactory(factory());
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new StringRedisSerializer());
    template.setEnableTransactionSupport(false);
    return template;
  }


}
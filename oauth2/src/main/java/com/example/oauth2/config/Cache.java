package com.example.oauth2.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * 启用对缓存的支持 Created by hcj on 18-7-29
 */
@Configuration
@EnableCaching
public class Cache {

  @Autowired
  RedisConnectionFactory connectionFactory;
  @Bean
  public CacheManager concurrentMapCacheManager() {
    return new ConcurrentMapCacheManager("concurrentMap");
  }

//  @Bean
//  public CacheManager jCacheCacheManager() {
//    return new JCacheCacheManager("jCache");
//  }

//  @Bean
//  public CacheManager ehCacheCacheManager() {
//    EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
//    ehCacheCacheManager.setTransactionAware(true);
//    try {
//      InputStream inputStream = new ClassPathResource("static/ehcache.xml").getInputStream();
//
//      ehCacheCacheManager.setCacheManager(new net.sf.ehcache.CacheManager(inputStream));
//
//    }  catch (IOException e) {
//      e.printStackTrace();
//    }
//    return ehCacheCacheManager;
//  }

  @Bean
  public CacheManager redisCacheManager() {
    RedisCacheWriter redisCacheWriter = RedisCacheWriter.lockingRedisCacheWriter(connectionFactory);
    RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
    redisCacheConfiguration.usePrefix();
    return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration, true);
  }

  @Primary
  @Bean
  public CacheManager compositeCacheManager() {
    CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
    List<CacheManager> objects = new ArrayList<>();
    // 开启缓存 ，以此查看
//    objects.add(ehCacheCacheManager());
    objects.add(redisCacheManager());
    objects.add(concurrentMapCacheManager());
    compositeCacheManager.setCacheManagers(objects);
    return compositeCacheManager;
  }

}

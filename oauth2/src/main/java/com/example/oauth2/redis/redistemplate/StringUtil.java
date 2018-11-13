package com.example.oauth2.redis.redistemplate;

import com.example.oauth2.Resource.vm.TokenDetailsVM;
import java.util.Collection;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * byte[] <-> string Created by hcj on 18-7-25
 */
@Configuration
public class StringUtil implements InitializingBean {

  private static final String ACCESS = "access:";
  private static final String AUTH_TO_ACCESS = "auth_to_access:";
  private static final String AUTH = "auth:";
  private static final String REFRESH_AUTH = "refresh_auth:";
  private static final String ACCESS_TO_REFRESH = "access_to_refresh:";
  private static final String REFRESH = "refresh:";
  private static final String REFRESH_TO_ACCESS = "refresh_to_access:";
  private static final String CLIENT_ID_TO_ACCESS = "client_id_to_access:";
  private static final String UNAME_TO_ACCESS = "uname_to_access:";



  @Autowired
  @Qualifier("redisTemplate")
  private RedisTemplate<String, Object> redisTemplate;

  private ValueOperations<String, Object> valueOperations;

  @Override
  public void afterPropertiesSet() throws Exception {
    // bean以实例话， 并进行了属性填充
    // factory 开启管道
    valueOperations = redisTemplate.opsForValue();
  }

  public ValueOperations<String, Object> getValueOperations() {
    return valueOperations;
  }
    // 更新问题，
//  public void findAllAuth() {
//    // 查询可以本地数据库查询， 和redis中的是一样的
//
//  }

  public OAuth2Authentication updateAuthrozition(TokenDetailsVM tokenDetailsVM) {
    String accessToken = tokenDetailsVM.getAccessToken();
    //    String authority = tokenDetailsVM.getAuthority();
    return  (OAuth2Authentication) valueOperations
        .get(AUTH + accessToken);



  }
}

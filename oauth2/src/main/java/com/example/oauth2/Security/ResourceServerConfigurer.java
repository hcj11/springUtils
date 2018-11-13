package com.example.oauth2.Security;

import com.example.oauth2.config.SubSessionRegistryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Created by hcj on 18-7-23
 */
@Configuration
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

//  @Override
//  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//    resources.w
//  }
  @Autowired
  SubSessionRegistryImpl subSessionRegistry;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    // 定义用户资源访问的过期时间，
    http.authorizeRequests()
        .antMatchers("/get").
        access("hasAnyRole('ADMIN')")
        .antMatchers("/put").access("hasRole('USER')")
        .and().rememberMe().key("hcj").tokenValiditySeconds(10000)
        // 之一用户使用？ 最多同时有1个用户在线，超过的存在风险盗号风险
        // 或者生成 两个终端的token，同时代表一个人， 那么超过一个token就报错
        //   资源保护器， session 管理目标id，依然在本服务器继续维护，
        .and().sessionManagement().
        maximumSessions(1).sessionRegistry(subSessionRegistry);

  }
}

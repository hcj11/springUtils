package com.example.demo.security;

import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;

/**
 * web  拦截 Created by hcj on 18-7-22
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  //  @Bean
//  public SpringTemplateEngine springTemplateEngine(DefaultTemplateResolver templateResolver){
//    SpringTemplateEngine engine = new SpringTemplateEngine();
//    engine.setTemplateResolver(templateResolver);
//    engine.addDialect(new SpringSecurityDialect());
//    return engine;
//  }
//  private CustomUserDetailsService userDetailsService;
//
  @Autowired
  UserService userService;
  @Autowired
  RoleService roleService;

  // http 请求,
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // 在cookie中存储token  包括, username,password,key的hash
    // 框架提供的 cookie盗用的检查, 理论上并不能完全防止盗用,
    // 在为本人登录之前的操作, 盗用者都是可以操作你的账户的.
    // .exceptionHandling().and() 异常拦截处理,
    http.csrf().disable().formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/management/user")
        .failureHandler(new CustomFailureHandler())
        .and().httpBasic().realmName("hcj").and()
        .authorizeRequests()
        .antMatchers("/**/user/**").hasRole("ADMIN")
        .anyRequest().permitAll()
        .and().requiresChannel().antMatchers("/**/user/**")
        .requiresSecure()
        .antMatchers("/").requiresInsecure()
        .and().rememberMe()
        .tokenRepository(new InMemoryTokenRepositoryImpl())
        .tokenValiditySeconds(86400).
        key("hcj").and().sessionManagement().maximumSessions(1)
        .expiredUrl("/login")
    ;
//       .authenticationProvider()
    //maximumSessions 当大于 2 时, 最先登录的用户就会失效,  可以采用其他的策略
    // ConcurrencyControlConfigurer 并发控制-> session变换,
    // invalidSessionStrategy() maxSessionsPreventsLogin
    // maxSessionsPreventsLogin(true); 阻止登录,
    // sessionRegistry(s) session 的生成策略, 每次生成的session都不同, 防止固定攻击,sessionId 登录后会重新生成一个sessionId
//        .sessionManagement().sessionCreationPolicy()
//       .invalidSessionStrategy(new CustomInvalidSessionStrategy("/login"))

  }

  // 权限管理
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // 权限验证
    auth.userDetailsService(new CustomUserDetailsService(userService, roleService));
  }

  // web管理
  @Override
  public void configure(WebSecurity web) throws Exception {
    // web 过滤器
    super.configure(web);
  }
}

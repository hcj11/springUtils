package com.example.oauth2;

import com.example.oauth2.Resource.VoteVM;
import com.example.oauth2.domain.Article;
import com.example.oauth2.domain.User;
import com.example.oauth2.repository.ArticleRepository;
import com.example.oauth2.service.ArticleService;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Oauth2ApplicationTests implements InitializingBean
{

  @Autowired
  ArticleService articleService;
  @Autowired
  ArticleRepository articleRepository;
    public final HashMap<Long, Long> map = new HashMap<>();
  @Override
  public void afterPropertiesSet() throws Exception {

    List<Article> all = articleRepository.findAll();
    for (Article article:all){
      this.map.put(article.getId(),article.getUserId());
    }
  }
  @Test
  public void contextLoads() {
    // 10 万
    for (int i = 0; i < 100000; i++) {
      Article article = new Article("文章主旨" + i + "!!!", "hello world" + i,
          "内容简介", "作者信息", (long) i);
      articleService.save(article);
    }
  }


  @Test
  public void vote(){
    Random random = new Random();
    for (int i=1;i<1000000;i++){
      int i1 = random.nextInt(10000);
      int i2 = random.nextInt(700) + 1;// 1-300
      Long aLong = this.map.get((long)i2);
      String url = "http://localhost:8080/vote";
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
      VoteVM voteVM = new VoteVM();
      voteVM.setArticleId(i2+"");
      voteVM.setUserId(i1+""); // 投票人
      voteVM.setAuthorId(aLong+"");
      HttpEntity<VoteVM> entity = new HttpEntity<>(voteVM, headers);
      HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
      RestTemplate restTemplate = new RestTemplate(requestFactory);
      restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
    }
  }
  @Test
  public void  publishArticle(){
    Random random = new Random();
    for (int i=300;i<600;i++){
      int i1 = random.nextInt(10000);
      String url = "http://localhost:8080/article";
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
      Article article = new Article();
      article.setArticleName("login-"+i1+"的文章");
      article.setAuthorname("login-"+i1);
      article.setContent("login-"+i1+"的文章内容");
      article.setIntro("login-"+i1+"的简介");
      article.setUserId((long)i1);
      HttpEntity<Article> entity = new HttpEntity<>(article, headers);
      HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
      RestTemplate restTemplate = new RestTemplate(requestFactory);
      restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
    }
  }
  @Test
  public void  register(){
  // 10 万
    for (int i=1;i<100000;i++){
      String url = "http://localhost:8080/user";
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
      User user = new User();
      user.setLogin("login-"+i);
      user.setFirstName("哈哈"+i);
      user.setLastName("呵呵"+i);
      user.setPassword("123");
      user.setRoleId(1L);
      HttpEntity<User> entity = new HttpEntity<>(user, headers);
      HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
      RestTemplate restTemplate = new RestTemplate(requestFactory);
      restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
    }
  }


  @Test
  public void addToken() {
    String url = "http://localhost:8080/oauth/token";
    HttpHeaders headers = new HttpHeaders();
    LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add("grant_type", "password");
    map.add("scope", "webclient");
    map.add("username", "17317298371");
    map.add("password", "123");
    RestTemplate restTemplate = new RestTemplate();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.add("Authorization", "Basic ZWFnbGVleWU6MTIz");

    HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
        map, headers);
    ResponseEntity<Object> result = restTemplate.exchange(url
        , HttpMethod.POST, requestEntity,
        Object.class);

    LinkedMultiValueMap<String, Object> body = (LinkedMultiValueMap<String, Object>) result.getBody();
    System.out.println(body);

  }



}

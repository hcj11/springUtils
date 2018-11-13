package com.example.oauth2.Resource;

import com.example.oauth2.Resource.vm.ArticleVM;
import com.example.oauth2.Resource.vm.PageUtil;
import com.example.oauth2.domain.Article;
import com.example.oauth2.service.ArticleService;
import java.text.NumberFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hcj on 18-7-26
 */
@RequestMapping("/article")
@RestController
public class ArticleResource {
  @Autowired
  ArticleService articleService;
  @Autowired
  DefaultTokenServices defaultTokenServices;
  // 新增文章
  @PostMapping
  public void  postArticle(@RequestBody Article article){
    articleService.save(article);
  }

  @GetMapping("vote")
  public ResponseEntity<List<ArticleVM>> getByVote(@RequestParam(value = "pageSize") String pageSize){
    PageUtil pageUtil = new PageUtil(Long.parseLong(pageSize),1);
    return ResponseEntity.ok(articleService.getAllByVote(pageUtil));
  }

  @GetMapping("time")
  public ResponseEntity<List<ArticleVM>> getByTime(@RequestParam(value = "pageSize") long pageSize ){
    PageUtil pageUtil = new PageUtil(pageSize,1);
    return ResponseEntity.ok(articleService.getAllByTime(pageUtil));
  }

  @GetMapping("sum")
  public ResponseEntity<List<ArticleVM>> getBySum(@RequestParam(value = "pageSize") long pageSize ){
    PageUtil pageUtil = new PageUtil(pageSize,1);
    return ResponseEntity.ok(articleService.getAllBySum(pageUtil));
  }

  @GetMapping
  public ResponseEntity findAll(){
    return ResponseEntity.ok(articleService.articles());
  }

    @DeleteMapping("{id}")
    public void delete(OAuth2Authentication authentication, @PathVariable(value = "id")Long id){

      OAuth2AccessToken accessToken = defaultTokenServices.getAccessToken(authentication);
      DefaultOAuth2AccessToken accessToken1 = (DefaultOAuth2AccessToken) accessToken;
      String nickname =  String.valueOf(accessToken1.getAdditionalInformation().get("firstname"));

      articleService.delete(id,nickname);
    }
}

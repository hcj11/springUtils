package com.example.oauth2.Resource;

import com.example.oauth2.domain.Article;
import com.example.oauth2.service.ArticleService;
import com.example.oauth2.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 名词vote
 * Created by hcj on 18-7-26
 */
@RequestMapping("/vote")
@RestController
public class VoteResource {
  @Autowired
  VoteService voteService;

  @PostMapping
  public void  postVote(@RequestBody VoteVM voteVM){
    voteService.postVote(voteVM);
  }
 

//  @GetMapping
//  public ResponseEntity findAll(){
//    return ResponseEntity.ok(articleService.articles());
//  }

}

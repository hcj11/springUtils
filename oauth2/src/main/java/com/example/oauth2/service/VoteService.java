package com.example.oauth2.service;

import com.example.oauth2.Resource.VoteVM;
import com.example.oauth2.redis.VoteTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by hcj on 18-7-26
 */
@Component
public class VoteService {
  @Autowired
  VoteTokenStore voteTokenStore;

  public void postVote(VoteVM voteVM) {
    voteTokenStore.postVote(voteVM);
  }
}

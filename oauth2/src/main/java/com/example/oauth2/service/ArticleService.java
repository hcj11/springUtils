package com.example.oauth2.service;

import com.example.oauth2.Resource.vm.ArticleVM;
import com.example.oauth2.Resource.vm.PageUtil;
import com.example.oauth2.domain.Article;
import com.example.oauth2.redis.VoteTokenStore;
import com.example.oauth2.repository.ArticleRepository;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hcj on 18-7-26
 */
@Service
public class ArticleService  implements InitializingBean{

  @Autowired
  ArticleRepository articleRepository;
  @Autowired
  VoteTokenStore voteTokenStore;
  @Autowired
  UserService userService;


//  public final HashMap<Long, Long> map = new HashMap<>();


  @Override
  public void afterPropertiesSet() throws Exception {

  }

  @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
  public void save(Article article){
    article.setCreatedBy(article.getAuthorname());
    // 冗余.
    Article save = articleRepository.save(article);
    // 新增redis 数据
    voteTokenStore.addRank(save);
  }
  @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
  public void delete(Long id, String nickname){
    // 确定到时候是该文章的作者

    Article article = articleRepository.findById(id).orElse(null);
    if(article!=null && article.getAuthorname().equals(nickname)){
      articleRepository.deleteById(id);
      // 删除文章对应的排名信息
      voteTokenStore.deleteArticle(id,article.getId());
    }


  }


  @Transactional(readOnly = true)
  public Article  findOne(Long articleId){
    return articleRepository.findById(articleId).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<Article>  articles(){
    return articleRepository.findAll();
  }

  public List<ArticleVM> getAllByVote(PageUtil pageUtil) {
    return voteTokenStore.getAllByCondition(pageUtil,VoteTokenStore.VOTE_ORDER_STAT);

  }

  public List<ArticleVM> getAllByTime(PageUtil pageUtil) {
    return voteTokenStore.getAllByCondition(pageUtil,VoteTokenStore.TIME_ORDER_STAT);
  }

  public List<ArticleVM> getAllBySum(PageUtil pageUtil) {
    return voteTokenStore.getAllByCondition(pageUtil,VoteTokenStore.SUM_ORDER_STAT);
  }


}

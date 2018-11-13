package com.example.oauth2.redis;

import com.example.oauth2.Resource.VoteVM;
import com.example.oauth2.Resource.vm.ArticleVM;
import com.example.oauth2.Resource.vm.PageUtil;
import com.example.oauth2.domain.Article;
import com.example.oauth2.redis.redistemplate.HashUtil;
import com.example.oauth2.redis.redistemplate.SetUtil;
import com.example.oauth2.redis.redistemplate.ZsetUtil;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * Created by hcj on 18-7-26
 */
@Component
public class VoteTokenStore {

  public static final String TIME_ORDER_STAT = "time_order_stat:";
  public static final String VOTE_ORDER_STAT = "vote_order_stat:";
  public static final String SUM_ORDER_STAT = "sum_order_stat:";
  public static final String ARTICLE_INFO = "article_info:"; // 文章
  public static final String ARTICLE_VOTE_USER = "article_vote_user";
//  private static final String USER_INFO = "user_info:";


  public final static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
  private final static JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
  private static final long initTime = 1532614851;
  @Autowired
  ZsetUtil zsetUtil;
  @Autowired
  SetUtil setUtil;
  @Autowired
  HashUtil hashUtil;

  // 新增用户
  public void addRank(Article save) {
    String articleId = String.valueOf(save.getId());
    Long userId = save.getUserId();

    long l = (Instant.now().getEpochSecond() - initTime) / 3600;
    zsetUtil.getZSetOperations().add(TIME_ORDER_STAT, "articleId:" + articleId + ":" + userId, l);
    zsetUtil.getZSetOperations().add(VOTE_ORDER_STAT, "articleId:" + articleId + ":" + userId, 1L);
    zsetUtil.getZSetOperations()
        .add(SUM_ORDER_STAT, "articleId:" + articleId + ":" + userId, l + 1L);
    ArticleInfo articleInfo = new ArticleInfo(save.getIntro(), save.getContent(),
        save.getCreatedDate().toString(), save.getAuthorname());
    HashMap<Object, Object> map = new HashMap<>();
    map.put("articleId:" + articleId + ":" + userId, articleInfo);
    hashUtil.getHashOperationsY().putAll(ARTICLE_VOTE_USER, map);

  }

  public void postVote(VoteVM voteVM) {
    // todo 开流水线
    String articleId = voteVM.getArticleId();
    String userId = voteVM.getUserId();
    String authorId = voteVM.getAuthorId();
    Boolean member = setUtil.getSetOperations()
        .isMember(ARTICLE_INFO + articleId, userId);
    if (!member) {
      // 先查看当天是否够3次， 投票前查看是否超过三次， 进行通知，


      RedisCallback redisCallback = new RedisCallback<Object>() {
        @Override
        public Object doInRedis(RedisConnection connection) throws DataAccessException {
          connection.openPipeline();
          byte[] serialize = stringRedisSerializer.serialize(ARTICLE_INFO + articleId);
          byte[] serialize1 = jdkSerializationRedisSerializer.serialize(userId);
          byte[] serialize2 = stringRedisSerializer.serialize(VOTE_ORDER_STAT);
          byte[] serialize3 = stringRedisSerializer
              .serialize("articleId:" + articleId + ":" + authorId);
          byte[] serialize4 = stringRedisSerializer.serialize(SUM_ORDER_STAT);

          connection.setCommands().sAdd(serialize, serialize1);
          connection.zSetCommands().zIncrBy(serialize2, 1, serialize3);
          connection.zSetCommands().zIncrBy(serialize4, 1, serialize3);
          return connection.closePipeline();
        }
      };
      Object execute = zsetUtil.getRedisTemplateZ().execute(redisCallback, false, true);
      System.out.println(execute);
//      setUtil.getSetOperations()
//          .add(ARTICLE_INFO + articleId, userId);
      // 相关zset修改
//      zsetUtil.getZSetOperations()
//          .incrementScore(VOTE_ORDER_STAT, "articleId:" + articleId + ":" + authorId, 1);
//      zsetUtil.getZSetOperations()
//          .incrementScore(SUM_ORDER_STAT, "articleId:" + articleId + ":" + authorId, 1);

    }

  }
//  public Set<TypedTuple<Object>>  zsetFind(long l, long pageSize,String typeKey){
//
//    return typedTuples;
//  }

  public List<ArticleVM> getAllByCondition(PageUtil pageUtil, String typeKey) {
    if (pageUtil == null) {
      pageUtil = new PageUtil();
    }
    long page = pageUtil.getPage();
    long pageSize = pageUtil.getPageSize();
    long l = (page - 1) * pageSize;
    Long size = zsetUtil.getZSetOperations().size(typeKey);
    Set<TypedTuple<Object>> typedTuples = zsetUtil.getZSetOperations()
        .reverseRangeByScoreWithScores(typeKey, 0, size, l, pageSize);

    List<ArticleVM> articleVMs = new ArrayList<>();

    for (TypedTuple<Object> typedTuple : typedTuples) {
      ArticleVM articleVM = new ArticleVM();
      articleVM.setScore(String.valueOf(typedTuple.getScore()));

      LinkedHashMap map = (LinkedHashMap) hashUtil.getHashOperationsY()
          .get(ARTICLE_VOTE_USER, typedTuple.getValue().toString());

      articleVM.setIntro((String) map.get("intro"));
      articleVM.setTime((String) map.get("time"));
      articleVM.setUrl((String) map.get("url"));
      articleVM.setUsername((String) map.get("username"));
      articleVMs.add(articleVM);
    }
    return articleVMs;
  }

  public void deleteArticle(Long articleId, Long authorId) {
    // 全部删除
    String value = "articleId:" + articleId + ":" + authorId;
    zsetUtil.getZSetOperations().remove(TIME_ORDER_STAT, value);
    zsetUtil.getZSetOperations().remove(VOTE_ORDER_STAT, value);
    zsetUtil.getZSetOperations().remove(SUM_ORDER_STAT, value);
  }

//  public void addMessageWithPipeline(Set<Tuple> tuples) {
//    String s = StringSTR + RandomUtil.generateActivationKey();
//    byte[] serialize = stringRedisSerializer.serialize(s);
//
//    RedisCallback<List<Object>> redisCallback = new RedisCallback<List<Object>>() {
//
//      public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
//        connection.openPipeline();
//        connection.zAdd(serialize, tuples);
//        return connection.closePipeline();
//      }
//    };
//    List<Object> execute = redisTemplateZ.execute(redisCallback, false, true);
//    for (Object object : execute) {
//      System.out.println(object.toString());
//    }
//  }

}

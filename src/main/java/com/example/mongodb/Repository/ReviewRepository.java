package com.example.mongodb.Repository;

import com.example.mongodb.domain.Review;
import java.util.List;
import java.util.Map;
import org.bson.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by hcj on 18-8-3
 */
public interface ReviewRepository extends MongoRepository<Review,String>{
  // $rating 引用上下文 不要有其他注释之类  , 另出现聚合冲突，可以尝试其他的查询
  @Query(value = "["
      + "    {"
      + "      $match: {"
      + "        \"product_id\": ?0"
      + "      }"
      + "    },"
      + "    {"
      + "      $group: {"
      + "         _id : '$product_id'"
      + "         ,count:{$sum :1}"
      + "         ,avg: {$avg: '$rating'}"
      + "      }"
      + "    }"
      + "  ]")
  Object aggregation(String product_id);
}

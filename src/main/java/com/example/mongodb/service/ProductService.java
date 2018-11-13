package com.example.mongodb.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.example.mongodb.Repository.ProductRepository;
import com.example.mongodb.domain.Product;
import com.mongodb.client.result.UpdateResult;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.bson.BsonString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * Created by hcj on 18-8-3
 */
@Service
public class ProductService {
  @Autowired
  ProductRepository productRepository;
  @Autowired
  MongoOperations mongoOperations;

  public void updateReviewsExistRating(String review_id){
    /**查询 */
    Aggregation agg = newAggregation(Aggregation.
            match(Criteria.where("_id").is(review_id)),
        project("product_id", "rating")
    );
    AggregationResults<Map> results = mongoOperations.aggregate(agg, "review", Map.class);
    Map<String, Object> map = results.getMappedResults().get(0);
    String product_id = (String) map.get("product_id");
    Double rating = (Double)map.get("rating");

    Aggregation agg2 = newAggregation(Aggregation.
            match(Criteria.where("_id").is(product_id)),
        project("ratings_total","total_reviews")
    );
    /**更新分数 */
    AggregationResults<Map> results2 = mongoOperations.aggregate(agg2, "product", Map.class);
    Map<String, Object> map2 = results2.getMappedResults().get(0);
    Double total = (Double) map2.get("ratings_total");
    Double v = (Double) map2.get("total_reviews");
    total+=rating;
    updateProduct(product_id, total, v + 1d);

  }
  public void updateReviews(String product_id){

    Aggregation agg = newAggregation(Aggregation.
            match(Criteria.where("product_id").is(product_id)),
        project("product_id", "rating"),
        group("product_id").count().
            as("count").sum("rating")
            .as("total")
    );
    AggregationResults<Map> results = mongoOperations.aggregate(agg, "review", Map.class);
//    确定产品id
    Map<String,Object> mappedResults =(Map<String,Object>) results.getMappedResults().get(0);
    // 注意此处是Integer not double
    Integer count = (Integer) mappedResults.get("count");
    // this is type of Double
    Double total = (Double) mappedResults.get("total");
    updateProduct(product_id, total,count*1.0d);


  }

  /**修改评分，
   * Created by hcj on 18-8-4.
   */
  private void updateProduct(String product_id, Double total,Double count) {
    try {
      double v = total / count ;
      DecimalFormat df = new DecimalFormat("0.00");
      // todo 存在即可直接求和
      double v1 = df.parse(df.format(v)).doubleValue();
      UpdateResult updateResult = mongoOperations.updateFirst(
          query(Criteria.where("_id").is(product_id)), new Update().inc("total_reviews", 1d)
              .set("average_reviews", v1).set("ratings_total", total)
          , Product.class);
      System.out.println(updateResult);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public List<Product> save(Collection<Product> product){

    return productRepository.saveAll(product);
  }
}

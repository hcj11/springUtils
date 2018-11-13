package com.example.mongodb;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.fields;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.skip;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import com.example.mongodb.Repository.ReviewRepository;
import com.example.mongodb.Repository.UserRepository;
import com.example.mongodb.domain.Order;
import com.example.mongodb.domain.Product;
import com.example.mongodb.service.ProductService;
import com.mongodb.client.result.UpdateResult;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 聚合校验 Created by hcj on 18-8-4
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AggregateValidate {

  //
  @Autowired
  MongoOperations mongoOperations;
  @Autowired
  ReviewRepository reviewRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  ProductService productService;
  @Test
  public void update(){
    productService.updateReviewsExistRating("105");

  }
  @Test
  public void  updateAnyOne(){
    // 操作符更新 根据查询 ， 更新 固定的 ，只更新匹配的第一个
    // todo 更新订单中的总价格 ,
//    mongoOperations.updateFirst(query(Criteria.where("sub_total").is("")),update());
    // 单更新
//    UpdateResult updateResult = mongoOperations
//        .updateFirst(query(Criteria.where("total_reviews").is(1d)),
//            update("total_reviews", 2d), Product.class);

//    UpdateResult updateResult = mongoOperations
//        .updateFirst(query(Criteria.where("total_reviews").is(2d)),
//            new Update().inc("total_reviews", 1d), Product.class);
 // 1. 查询该商品的总评分 和平均分
    String product_id="101";
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
    double v = total / count ;
    NumberFormat.getNumberInstance(Locale.CHINA).setRoundingMode(RoundingMode.HALF_UP);
    DecimalFormat df = new DecimalFormat("0.00");
    try {
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

  @Test
  public void aggregationOper() {
    String product_id = "100";
    // 聚合 操作 转成目标文档
//    Object documents= reviewRepository.aggregation(product_id);
//    System.out.println(documents);
    // 尝试提供的mongoOperations进行操作 $product_id 无需添加引用
    /**
     * 简单查询:
     *     Aggregation agg = newAggregation(Aggregation.
     match(Criteria.where("product_id").is(product_id))
     , project("product_id", "rating","_id","title"),
     group("product_id").count().as("count")
     .avg("rating").as("rating")
     .push("title").as("title")
     ,limit(1)
  排序:
     Aggregation.
     match(Criteria.where("product_id").is(product_id))
     , project("product_id", "rating","_id","title"),
     group("_id").count().as("count")
     .avg("rating").as("rating")
     .push("title").as("title")
     ,sort(Direction.DESC,"_id")
     ,limit(3)


     * */
  // 查询类别对应的 产品量
    /**    Aggregation agg = newAggregation(
     Aggregation.project("category_ids"),
     unwind("category_ids"), // 为数组中每个 category_id 和 product  建立文档关系
     group("category_ids").count().as("count")
     );
     * */
    /**
     * 按照年，月查询
     *   Instant from = Instant.now().minus(100 * 30, ChronoUnit.DAYS);
     System.out.println("time:" +from);
     Aggregation agg = newAggregation(
     Aggregation.match(Criteria.where("purchase_date").gte(from)),
     project("sub_total").
     andExpression("year(purchase_date)").as("year")
     .andExpression("month(purchase_date)").as("month")
     ,
     group(fields().and("year").and("month")).count().as("count")
     .sum("sub_total").as("sub_total")  );
     * */
    //  查询上海最好的客户 >50 -> sh
    // >100的滆湖
  /**
   *
   *  Aggregation agg = newAggregation(
   project("shipping_address","user_id","sub_total")
   // 注意 此处shipping_address.zip 数组全匹配 ，so，想精准则直插入一个地址,
   ,Aggregation.match(Criteria.where("shipping_address.zip").gte(5))
   ,group("user_id").sum("sub_total").as("sub_total")
   //        ,match(Criteria.where("sub_total").gte(100))
   ,sort(Direction.DESC,"sub_total")
   );
   * */
  /**
   *  long pagenumber = 1;
   long pageSize =5;
   // 分页
   Aggregation agg = newAggregation(
   project("shipping_address","user_id","sub_total")
   // 注意 此处shipping_address.zip 数组全匹配 ，so，想精准则直插入一个地址,
   ,Aggregation.match(Criteria.where("shipping_address.zip").gte(5))
   ,group("user_id").sum("sub_total").as("sub_total")
   //        ,match(Criteria.where("sub_total").gte(100))
   ,skip((pagenumber - 1)*pageSize)
   ,limit(pageSize)
   ,sort(Direction.DESC,"sub_total")
   );
   * */
//  String ss="";
//  ss.concat().concat().substring()
    // 重塑文档
    /**    Aggregation agg = newAggregation(
     project()
     .andExpression("substr(first_name,0,3)").as("first_init")
     .andExpression("toUpper(username)").as("usernameUpper")
     .andExpression("concat(substr(first_name,0,3),'-',last_name)")
     .as("common_name")

     Aggregation agg = newAggregation(
     project().
     and("details.weight").multiply(10).as("multiply")
     .and("details.weight").plus(10).as("plus")
     .and("details.weight").minus(10).as("minus")
     .and("details.weight").mod(10).as("mod")
     .and("details.weight").divide(10).as("divide")
     * */
    // sort 配合 first and last 使用
    // 具体参考: https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongo.aggregation.examples.example5
//    Aggregation agg = newAggregation(
//         project("sub_total")
//        ,sort(Direction.DESC,"sub_total")
//        ,group("sub_total").
//            first("").as("")
//        ,project().and("sub_total").
//    );
    // distinct  and("").previousOperation() group 之后,获取之前保存的数据
    /**
     *    Aggregation agg = newAggregation(
     project().and("_id").substring(0,2)
     .as("_id").and("user_id").as("user_id")
     ,group("_id","user_id").count().as("count")
     // previousOperation 获取前面的数据_id   并以count别名
     ,project().and("_id").previousOperation()
     .and("count").as("count")
     //        ,project("user_id").and("_id").previousOperation()
     //        .and("user_id").as("user_id")
     * */
    // 根据排名获取 用户的数量
    Aggregation agg = newAggregation(
        project().and("_id").substring(0,2)
            .as("_id").and("user_id").as("user_id")
        ,group("_id").
            addToSet("user_id").as("user_id")
        // previousOperation 获取前面的数据_id   并以count别名
        ,project("_id","user_id").and("user_id").size()
            .as("count")
    );
    AggregationResults<Map> results = mongoOperations.aggregate(agg, "order", Map.class);
    System.out.println("映射结果集: ");
    List<Map> mappedResults = results.getMappedResults();
    // 返回 -> linkHashMap
    System.out.println(mappedResults);

  }

}

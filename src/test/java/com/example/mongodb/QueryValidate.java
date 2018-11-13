package com.example.mongodb;

import com.example.mongodb.Repository.OrderRepository;
import com.example.mongodb.Repository.ProductRepository;
import com.example.mongodb.domain.Category;
import com.example.mongodb.domain.ItemVM;
import com.example.mongodb.domain.Order;
import com.example.mongodb.domain.Product;
import com.example.mongodb.domain.Review;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import java.lang.reflect.Array;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.DbCallback;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by hcj on 18-8-3
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QueryValidate {
  @Autowired
  MongoOperations mongoOperations;
  @Autowired
  ProductRepository productRepository;
  @Autowired
  MongoTemplate mongoTemplate;
  @Autowired
  OrderRepository orderRepository;

  // 各种查询验证
  @Test
  public void  list(){
    // 只获取单个数据？ 方法命名约定
//    List<Product> products = productRepository.findByName("牛轧糖B型");
//    List<Product> all = productRepository.findAll();
//    List<String> collect = all.stream().map(Product::getId).collect(Collectors.toList());
//    List<Category> byIdIn = productRepository.findByIdIn(collect);
//    System.out.println(byIdIn);
  // 如何正确限制 -> 分页
//    int limit=5; // 0 index
//    PageRequest of = PageRequest.of(0, limit);
//    Page<Product> alls =productRepository.findAll(of);
//    System.out.println(alls.getTotalPages());
//    System.out.println(alls.getContent());
    // 时间比较   Between(time) or Greater(num)
//    Instant plusstart = Instant.now().plus(-1, ChronoUnit.DAYS);
//    Instant plusend = Instant.now().plus(4, ChronoUnit.DAYS);
//    List<Order> list = orderRepository.findByPurchaseDateBetween(plusstart, plusend);
    // 大小比较 double or long 进行比较
//    List<Order> list  = orderRepository.findBySubTotalBetween(11,40);
//    System.out.println(list);
    // 如何匹配子文档 ? 自定义 yes
    List<Order> allBySku = orderRepository.findAllBySku("1231");
    System.out.println(allBySku);
// 数组测试 elemMatch
//    orderRepository.findAllBySkuWith()

    // 模拟查询条件
//    List<ItemVM> itemVMS = new ArrayList<>();
//    ItemVM itemVM = new ItemVM();
//    itemVM.setQuantity(2d);
//    itemVMS.add(itemVM);
//    List<Order> orders = mongoOperations
//        .find(Query.query(Criteria.where("line_items").is(itemVMS)), Order.class);
//    System.out.println(orders);
    // 需要注意参数类型
    List<Order> allBySkuWithMultiCondition = orderRepository
        .findAllBySkuWithMultiCondition("1232", 11d);
    System.out.println(allBySkuWithMultiCondition);
    // 聚合匹配策略 尝试, 查询选择
//    mongoOperations.aggregate()


//    mongoOperations.find(Query.query(Criteria.where("order").elemMatch()))
//    mongoOperations.execute(new DbCallback<Object>() {
//      @Override
//      public Object doInDB(MongoDatabase db) throws MongoException, DataAccessException {
//        return null;
//      }
//    })
  }

  @Test
  public void operate(){
    mongoOperations.indexOps(Review.class).dropIndex("time_field");

    // String 文档过期策略
    Index index = new Index();
    // ttl时间
    index.expire(100, TimeUnit.DAYS).named("time_field")
    .on("date", Direction.ASC);
    String s = mongoOperations.indexOps(Review.class).ensureIndex(index);

    System.out.println(s);


  }

}

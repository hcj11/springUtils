package com.example.mongodb.Repository;

import com.example.mongodb.domain.Order;
import com.example.mongodb.domain.Review;
import java.time.Instant;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by hcj on 18-8-3
 */
public interface OrderRepository extends MongoRepository<Order,String>{
  // 注意_ 不解析问题
  List<Order> findByPurchaseDateBetween(Instant plusstart, Instant plusend);


  List<Order> findBySubTotalBetween(int i, int i1);

  @Query(value = "{}",fields = "{'_id' : 1,'line_items':1}")
  List<Order> findAlls();

  // 根据子文档查询 索引查找 只返回需要列
  @Query(value = "{'line_items.sku' : '?0' }",fields = "{'user_id' : 1,'id' : 1}")
  List<Order> findAllBySku(String sku);
  // 根据两个条件查询 {"line_items.sku": "1232" , 'line_items.quantity': {$gte:11}}
  // 此处 查询选择无效，
  @Query(value = "{'line_items.sku' : '?0','line_items.quantity': {$gte : ?1 } }",fields = "{'user_id' : 0,'id' : 0}")
  List<Order> findAllBySkuWithMultiCondition(String sku,Double quantity);

  // no way
//  List<Order> findAllByLineItems(String sku);


}

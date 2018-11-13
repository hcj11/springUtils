package com.example.mongodb.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.skip;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.example.mongodb.Repository.OrderRepository;
import com.example.mongodb.domain.AuthDoc;
import com.example.mongodb.domain.ItemVM;
import com.example.mongodb.domain.Order;
import com.example.mongodb.domain.enumVO.STATE;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * Created by hcj on 18-8-3
 */
@Service
public class OrderService {

  @Autowired
  OrderRepository orderRepository;
  @Autowired
  MongoOperations mongoOperations;

  private static final ReentrantLock reentrantLock = new ReentrantLock();

  public void findAll(){
    long pageSize=10L;
    Random random = new Random(47);
//    0-499999
    long page = (long)random.nextInt(50000);
    // 分页, 全文扫描的分页 查询， -> 待优化，
    Aggregation aggregation = newAggregation(
        match(Criteria.where("sub_total").gte(160))
        ,project("user_id","line_items")
        ,skip(page*pageSize),limit(pageSize)
        ,sort(Sort.Direction.ASC,"user_id")
    );
    AggregationResults<Order> order = mongoOperations.aggregate(aggregation, "order", Order.class);
    List<Order> mappedResults = order.getMappedResults();
    System.out.println(mappedResults.size());


  }

  // todo 待并发测试.
  public void sumbitOrder(String user_id, Double oldTotal) {
    String id = "1015";
    // 查询更新前的总额
    String product_id = "101";
    // 客户提交前的值

    // 用户下了多个订单? 更新状态 not found -> newDoc 只一个线程可以成功修改 ,
    /** 注意： 这里使用了状态判断, 其他线程导致 newDoc = null */
    Order newDoc = mongoOperations.findAndModify(query(Criteria.where("user_id").is(user_id)
            .and("state").is(STATE.CART)), new Update().set("state", STATE.PRE_AUTHORIZE)
        , FindAndModifyOptions.options().returnNew(true), Order.class);
    System.out.println(newDoc);

    // 查询更新情况
    if (newDoc != null && newDoc.getState() == STATE.PRE_AUTHORIZE && oldTotal
        .equals(newDoc.getSubTotal())) {
      try {
        // 更新状态
        mongoOperations.findAndModify(query(Criteria.where("user_id").is(user_id)
                .and("state").is(STATE.PRE_AUTHORIZE).and("sub_total").is(oldTotal)),
            new Update().set("state", STATE.AUTHORIZE)
            , Order.class);
        // 完成订单
        // 校验过程,,, 出错回滚到原状态
        AuthDoc authDoc = new AuthDoc(111L, 1112L, "Authorize.net");

        mongoOperations.findAndModify(query(Criteria.where("user_id").is(user_id)
                .and("state").is(STATE.AUTHORIZE)), new Update().set("state", STATE.PRE_SHIPPING)
                .set("authorization", authDoc)
            , Order.class);

      } catch (Exception e) {
        // 回滚到原状态
        mongoOperations.findAndModify(query(Criteria.where("user_id").is(user_id)
                .and("state").is(STATE.PRE_AUTHORIZE).and("sub_total").is(oldTotal)),
            new Update().set("state", STATE.CART)
            , Order.class);
        e.printStackTrace();

      }


    } else {
      // 更新回原值
      System.out.println("请不要重复提交!!!");
    }
  }

  public List<Order> save(Collection<Order> orders) {

    return orderRepository.saveAll(orders);
  }

  public void updateSub_total() {
    // 同样存在并发问题，没有事务，
    try {
      reentrantLock.tryLock(5, TimeUnit.SECONDS);
      List<Order> alls = orderRepository.findAlls();
      for (Order order : alls) {
        String id = order.getId();
        Collection<ItemVM> lineItems = order.getLineItems();
        double total = 0;
        for (ItemVM itemVM : lineItems) {
          double v = itemVM.getQuantity() * itemVM.getPrice().getRetail();
          total += v;
        }
        mongoOperations.updateFirst(query(Criteria.where("_id").is(id)),
            Update.update("sub_total", total), Order.class);
      }
    } catch (InterruptedException e) {
      System.out.println("超时报错，");
      e.printStackTrace();
    } finally {
      reentrantLock.unlock();
    }

    // 问题出在 update 怎么匹配  Collection 并且和 对应的id对起来
//    alls.stream().map(Order::getLineItems).forEach( items->{
//      LinkedHashSet items1 = (LinkedHashSet) items;
//
//    });

//    mongoOperations.findAndModify()

  }
}

package com.example.mongodb;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import com.example.mongodb.Repository.OrderRepository;
import com.example.mongodb.Repository.ReviewRepository;
import com.example.mongodb.domain.Address;
import com.example.mongodb.domain.CartItem;
import com.example.mongodb.domain.ItemVM;
import com.example.mongodb.domain.Order;
import com.example.mongodb.domain.Price;
import com.example.mongodb.domain.Review;
import com.example.mongodb.domain.enumVO.STATE;
import com.example.mongodb.service.OrderService;
import com.example.mongodb.service.ReviewService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Update.Position;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by hcj on 18-8-3
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {

  @Autowired
  OrderService orderService;
  @Autowired
  OrderRepository orderRepository;
  @Autowired
  MongoOperations mongoOperations;

  /**
   private String id;
   private String user_id;
   private Instant purchase_date = Instant.now();
   private STATE state;
   private Collection<ItemVM> line_items = new LinkedHashSet<ItemVM>();
   private Collection<Address> shipping_address = new LinkedHashSet<Address>();

   */
  @Test
  public void find(){
  }

  @Test
  public void createIndex(){
    Index index = new Index();
    index.named("line_items").on("line_items", Direction.ASC);
    String s = mongoOperations.indexOps(Order.class).ensureIndex(index);
    System.out.println(s);
  }
  @Test
  public void findAll(){
    orderService.findAll();

  }
  /** 提交购物车的订单*/
  @Test
  public void sumbitOrder(){
    for (int i=0;i<2;i++){
      orderService.sumbitOrder("192",50184.0d);
    }


  }

  /** 购物车 到订单  one step*/
  @Test
  public void createOrder(){
    mongoOperations.insert("");

    CartItem cartItem = new CartItem();
    // 产品id
    cartItem.setId("101");
    cartItem.setName("牛轧糖A型");
    cartItem.setSku("1232");
    cartItem.getPrice().setRetail(31d);
    cartItem.getPrice().setSale(20d);
    cartItem.setQuantity(2d);
    // 若购物车不存在就新增，存在就更新 主键问题
    // findAndModify() -> 原子
    //   mongoOperations.upsert() 也可以
    Order andModify = mongoOperations
        .findAndModify(query(Criteria.where("_id").is("10130").and("state").is(STATE.CART)
            ), new Update().inc("sub_total", cartItem.getQuantity() * cartItem.getPrice().getSale())
            .set("purchase_date",Instant.now()).set("user_id","111"),
            new FindAndModifyOptions().returnNew(true).upsert(true)
            , Order.class);
    System.out.println(andModify);
    addCartToOrder(cartItem);
  }

  /**two step */
  public void addCartToOrder(CartItem cartItem){
    // 更新购物车信息到文档 push -> cartItem 是数组？ yes
     mongoOperations.updateFirst(query(Criteria.where("_id").is("10130")
            .and("line_items._id").ne(cartItem.getId()).and("state").is(STATE.CART)),
        new Update().push("line_items", cartItem), Order.class);

  }

  /** immediately 即时更新 */
  @Test
  public void updateNum(){
    // 加入购物车，更新数据
    CartItem cartItem = new CartItem();
    cartItem.setId("101");
    cartItem.getPrice().setRetail(30d);
    cartItem.setSku("1232");
    cartItem.getPrice().setSale(20d);
    addCartToOrder(cartItem);

    double number = 2d;
    // 获取用户 iod , 以及订单id , 商品id, 商品单价
    double sale = cartItem.getPrice().getSale();
    mongoOperations.updateMulti(query(Criteria.where("_id").is("10130")
        .and("line_items._id").is(cartItem.getId()).and("state").is(STATE.CART)
        .and("user_id").is("111")),
        new Update().inc("line_items.$.quantity",number)
            .inc("sub_total", sale * number),
        Order.class);
  }

  // 删除购物车 商品
  @Test
  public void deleteCart(){
    Order state = mongoOperations.findAndRemove(query(Criteria.where("line_items._id").is("100")
            .and("state").is(STATE.CART))
        , Order.class);
    System.out.println(state);
  }

  @Test
  public void updateProductNum(){
    double number =1d;
    double sale =20d;
    // 执行细微的文档操作 $ 定位符的使用
    UpdateResult updateResult = mongoOperations
        .updateFirst(query(Criteria.where("line_items._id").is("100")
            .and("state").is(STATE.CART)), new Update().inc("line_items.$.quantity", -1 * number)
            .inc("sub_total", -1 * number * sale), Order.class);
    System.out.println(updateResult);
  }

  // 清空购物车
  @Test
  public void clearCart(){
    DeleteResult id = mongoOperations.remove(query(Criteria.where("_id").is("10130")), Order.class);
    System.out.println(id.getDeletedCount());
  }



  @Test
  public void updateSubTotal(){
    // 更新订单中的总价格 ,
    orderService.updateSub_total();
  }
  @Test
  public void updateAddressInfo(){
    // 更新 地址数量
    UpdateResult updateResult = mongoOperations
        .updateMulti(query(Criteria.where("shipping_address.name")
                .is("work")),
        // 栈删除
        new Update().pop("shipping_address", Position.LAST)
            // 精准删除 对于对象数组进行删除有困难》
//            new Update().pull("shipping_address.$", "")
            , Order.class);
    System.out.println(updateResult);

  }

  @Test
  public void save() {
    long l1 = System.currentTimeMillis();
    Random random = new Random();
    List<Order> orders = new ArrayList<>();
    for (int i=40;i<500000;i++){
      int i1 = random.nextInt(100) + 100;
      Order order = new Order();
      order.setUser_id(i1+"");
      order.setState(STATE.CART);
      // 不断的冗余 一个订单一个地址
      order.getLineItems().add(new ItemVM("100","1232","牛轧糖B型",54d+i1,new Price(12d+i1,10d+i1)));
      order.getShipping_address().add(new Address("home","homeStreet","homeCity","homeProvince",11d+i));
      order.setPurchaseDate(Instant.now().minus(i * 30 , ChronoUnit.DAYS));
      order.setId("101"+i);

      order.setSubTotal(37d+i1);
      orders.add(order);
    }

    // 批量插入
    mongoOperations.insert(orders,Order.class);
    long l2 = System.currentTimeMillis();
    long l = (l2 - l1) / 1000;
    System.out.println("耗时: "+l);
    // 比数据库快不知道多少倍了吧

  }

  @Test
  public void delete(){
    orderRepository.deleteAll();
  }

}

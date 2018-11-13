package com.example.mongodb;

import com.example.mongodb.Repository.TicksRepository;
import com.example.mongodb.Repository.UserRepository;
import com.example.mongodb.domain.Address;
import com.example.mongodb.domain.Details;
import com.example.mongodb.domain.Ticks;
import com.example.mongodb.domain.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbApplicationTests {

  @Autowired
  UserRepository userRepository;
  @Autowired
  TicksRepository ticksRepository;


  @Test
  public void save() {
    Collection<User> objects = new ArrayList<>();
    for (int i = 1; i < 2000; i++) {
      User user = new User();
      user.setFirst_name("hcj" + i);
      user.setLast_name("哈哈" + i);
      user.setHash("123");
      user.setUsername("login-hcj" + i);
      user.getPayment_methods().add("WEI_XIN");
      user.getPayment_methods().add("ZHI_FU_BAO");
      user.getAddresses()
          .add(new Address("shname" + i, "shstreet" + i, "sh", "shprovince" + i, 1d + i));
      // 批插入 ,没有主键约束,  尝试提供 其他主键，
      objects.add(user);
    }
    List<User> users = userRepository.saveAll(objects);
//    System.out.println(users);
  }

  @Test
  public void select() {
    // OrderByUsername
    Sort orders = new Sort(Direction.DESC, "username");
    PageRequest of = PageRequest.of(1, 1, orders);
    Page page = userRepository.findAll(of);
    System.out.println(page.getContent());
  }

  @Test
  public void update() {
    // 根据Id，查询
    String id = "102";
    int i = 2;
    User user = userRepository.findById(id).orElse(null);
    if (user != null) {
      user.setUsername("不要笑");
      // 未抛弃，
      user.getAddresses()
          .add(new Address("shname" + i, "shstreet" + i, "sh", "shprovince" + i, 1d + i));
      // 数据全部替换的方式，影响性能。
      userRepository.save(user);
    }
  }

  @Test
  public void delete() {
    String id = "5b63d67b1ed22459203a6949";
    // 只能全部删除
    userRepository.deleteAll();
  }

  @Test
  public void saveWithUnionPrimaryKey(){
    // mongodb 根据key 进行更新
    Ticks ticks = new Ticks();
    Details details = new Details(11d, "g", 111d, "福派源", "blue");
    ticks.setDetails(details);
    ticks.setName("联合主键测试，  update");
    ticksRepository.save(ticks);

  }

//  public static void main(String[] args) {
////    System.out.println(1d + 1); -> double
//    int i=2;
//    HashSet<Address> addresses =  new HashSet<Address>();
//    // 重写hashcode ?
//    Address sh = new Address("shname" + i, "shstreet" + i, "sh", "shprovince" + i, 1d + i);
//    addresses.add(sh);
//    addresses.add(sh);
//    System.out.println(addresses);
//  }

}

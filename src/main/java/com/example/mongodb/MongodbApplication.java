package com.example.mongodb;

import com.example.mongodb.Repository.UserRepository;
import com.example.mongodb.domain.Address;
import com.example.mongodb.domain.SubmitVO;
import com.example.mongodb.domain.User;
import com.example.mongodb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MongodbApplication {

  @Autowired
  OrderService orderService;
  @Autowired
  UserRepository userRepository;

  @GetMapping("save")
  public void save() {
    int i = 1;
    User user = new User();
    user.setFirst_name("hcj" + i);
    user.setLast_name("哈哈" + i);
    user.setHash("123");
    user.setUsername("login-hcj" + i);
    user.getPayment_methods().add("WEI_XIN");
    user.getPayment_methods().add("ZHI_FU_BAO");
    user.getAddresses()
        .add(new Address("shname" + i, "shstreet" + i, "sh", "shprovince" + i, 1d + i));
    userRepository.save(user);

  }

  @GetMapping("all")
  public void findAll() {
    orderService.findAll();
  }

  @PostMapping("submit")
  public void submit(@RequestBody SubmitVO submitVO) {
    orderService.sumbitOrder(submitVO.getUser_id(), submitVO.getSub_total());
  }


  public static void main(String[] args) {

    SpringApplication.run(MongodbApplication.class, args);
  }
}

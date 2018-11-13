package com.example.mongodb.Repository;

import com.example.mongodb.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 常用方法
 * Created by hcj on 18-8-3
 */
public interface UserRepository extends MongoRepository<User,String>{

//  Page findAllOrderByUsername(PageRequest of);
}

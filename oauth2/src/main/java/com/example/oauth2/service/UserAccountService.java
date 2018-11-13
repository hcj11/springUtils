package com.example.oauth2.service;

import com.example.oauth2.Resource.UserAccount;
import com.example.oauth2.repository.UserAccountRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hcj on 18-7-29
 */
@Service
public class UserAccountService {
  @Autowired
  UserAccountRepository userAccountRepository;

  // 使用组合缓存 compositeCacheManager
  @Cacheable(value = {"userAccount1","userAccount"})
  @Transactional(readOnly = true)
  public List<UserAccount> findAll() {
    return userAccountRepository.findAll();
  }

  // 切面记录文章点击量, 并存储到数据库中,
  @Cacheable(value = {"userAccount1","userAccount"})
  @Transactional(readOnly = true)
  public UserAccount findOne(Long id) {
    // 也可以自定义缓存，
    return userAccountRepository.findById(id).orElse(null);
  }

  @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
  public void save(UserAccount userAccount) {
    userAccount.setCreatedBy("张三");
    userAccountRepository.save(userAccount);
  }

  @CacheEvict(value = {"userAccount1","userAccount"})
  @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
  public void update(UserAccount userAccount) {
    userAccountRepository.save(userAccount);
  }



}

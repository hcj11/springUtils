package com.example.demo.service;

import com.example.demo.core.EntityCreatedEvent;
import com.example.demo.core.EntityUpdatedEvent;
import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.util.RandomUtil;

import java.time.Instant;
import java.util.List;
import java.util.Random;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hcj on 18-7-22
 */
@Component
public class UserService implements ApplicationContextAware {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    private ApplicationContext applicationContext;

    public boolean getPassword(String password, String oldPassword) {
        return bCryptPasswordEncoder.matches(password, oldPassword);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public User save(User user) {
        Random random = new Random();
//    int i = random.nextInt(2) + 1;// 1,2
//    String password = RandomUtil.generateResetKey();
        String encode = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        user.setCreatedBy(user.getLogin());
        user.setCreatedDate(Instant.now());
        user.setRoleId(1L); //long->int

        User save = userRepository.save(user);
        // 绑定数据时，发送更新事件,
        if (user.getId() == null) {
            applicationContext.publishEvent(new EntityCreatedEvent(user));
        } else {
            applicationContext.publishEvent(new EntityUpdatedEvent(user));
        }
        return save;
    }

    // 只读事务, 在事务读取期间不会数据前后误差问题
    // 优化orm ,无锁,  多用于统计结果的处理
    @Transactional(readOnly = true)
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    // 只读事务, 在事务读取期间不会数据前后误差问题
    // 优化orm ,无隔离,  多用于统计结果的处理
    @Transactional(readOnly = true)
    public User findOne(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public User findByName(String name) {
        // name 唯一性
        return userRepository.findByLogin(name).orElse(null);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

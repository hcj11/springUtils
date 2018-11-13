package com.example.redisdemo.Lock;

import com.example.redisdemo.redis.LockUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 未用到锁， Created by hcj on 18-7-27
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class LockDemo1 implements InitializingBean {
  private int count =0;
  private int sum =0;
  private final static ExecutorService service = Executors.newFixedThreadPool(10);
  private final Random random = new Random();
  private final long acquire_timeout = 10; // 获取锁的超时时间
  private final long lock_timeout = 10;
  private final String LOCK_KEY = "lock_key:";
  @Autowired
  ApplicationContext applicationContext ;
  @Autowired
  private  LockUtils lockUtils;

  @Override
  public void afterPropertiesSet() throws Exception {
     this.lockUtils = applicationContext.getBean("lockUtils", LockUtils.class);
  }

  // 下订单 synchronized
  public void addOrder(int _count) {
    long l = System.currentTimeMillis() + acquire_timeout;
    this.lockUtils.acquire_lock_with_timeout(LOCK_KEY,String.valueOf(l));
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    count+=_count;
    this.lockUtils.releaseLock(LOCK_KEY,String.valueOf(l));
  }

// synchronized 4950
  private  void Startpurchase() {

    ArrayList<Thread> objects = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      int finalI = i;
      Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          addOrder(finalI);
        }
      });
      thread.start();
      objects.add(thread);
    }

    for (Thread thread:objects){
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }


  public void test(){
    LockDemo1 lockDemo1 = new LockDemo1();
    lockDemo1.Startpurchase();
    System.out.println(lockDemo1.count);
  }


//  public static void main(String[] args) {
//    LockDemo1 lockDemo = new LockDemo1();
//    lockDemo.Startpurchase();
//    System.out.println(lockDemo.count);
//
//  }


}

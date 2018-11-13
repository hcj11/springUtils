package com.example.redisdemo.Test;

import com.example.redisdemo.RedisdemoApplication;
import com.example.redisdemo.redis.LockUtils;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 *  分布式锁， Created by hcj on 18-7-27
 */
@Configuration
public class LockDemo1 implements InitializingBean{

  @Override
  public void afterPropertiesSet() throws Exception {

  }
  private int count = 0;
  private int sum = 0;
  private final static ExecutorService service = Executors.newFixedThreadPool(10);
  private final Random random = new Random();
  private final long acquire_timeout = 10; // 获取锁的超时时间
  private final long lock_timeout = 10;
  private final String LOCK_KEY = "lock_key:";
  @Autowired
  private LockUtils lockUtils;

  public void setCount(int count) {
    this.count = count;
  }

  public int getCount() {
    return count;
  }

  // 下订单 synchronized
  public void addOrder(int _count) {
    long l = System.currentTimeMillis() + acquire_timeout;
    if(!this.lockUtils.acquire_lock_with_timeout(LOCK_KEY, String.valueOf(l))){
        throw new RuntimeException("请耐心等待。");
    }
    try {
      Thread.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    count += _count;
    this.lockUtils.releaseLock(LOCK_KEY, String.valueOf(l));
  }



  // synchronized 4950
//  public void Startpurchase() {
//
//    ArrayList<Thread> objects = new ArrayList<>();
//    for (int i = 0; i < 100; i++) {
//      int finalI = i;
//      Thread thread = new Thread(new Runnable() {
//        @Override
//        public void run() {
//          addOrder(finalI);
//        }
//      });
//      thread.start();
//      objects.add(thread);
//    }
//
//    for (Thread thread : objects) {
//      try {
//        thread.join();
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
//    }
//  }

//  public static void main(String[] args) {
//    AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(
//        RedisdemoApplication.class);
//    LockDemo1 lockDemo1 = configApplicationContext.getBean("lockDemo1", LockDemo1.class);
//    lockDemo1.Startpurchase();
//    System.out.println(lockDemo1.count);
//  }
//  public void test(){
//    LockDemo1 lockDemo1 = new LockDemo1();
//    lockDemo1.Startpurchase();
//    System.out.println(lockDemo1.count);
//  }

//  public static void main(String[] args) {
//    LockDemo1 lockDemo = new LockDemo1();
//    lockDemo.Startpurchase();
//    System.out.println(lockDemo.count);
//
//  }


}

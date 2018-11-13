package com.example.redisdemo.Lock;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 未用到锁， Created by hcj on 18-7-27
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class LockDemo {

  private static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
  private final static ExecutorService service = Executors.newFixedThreadPool(10);
  private final Random random = new Random();

  static {// 1-99  对key对应的值的修改，不会出现线程安全？
    for (int i = 1; i < 100; i++) {
      // 编号的商品  :  库存量
      map.put(i, i);
    }
  }

  // 下订单 synchronized
  public void addOrder() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // 100 次抢购， 可能会出现负数，
    for (int i = 0; i < 1000; i++) {
      int i1 = random.nextInt(99) + 1; // 1-9
      Integer integer = map.get(i1);
      // Exception in thread "main" java.lang.NullPointerException 删除时的线程不安全问题。
      if (integer > 0) {
        map.put(i1, --integer);
      }
    }

  }

  private void Startpurchase() {
    for (int i = 0; i < 100; i++) {
      service.submit(new Runnable() {
        @Override
        public void run() {
          addOrder();
        }
      });
    }
  }

  public static void main(String[] args) {
    LockDemo lockDemo = new LockDemo();
    lockDemo.Startpurchase();
    lockDemo.addOrder();
    System.out.println(map);
    service.shutdown();
    try {
      service.awaitTermination(100, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


}

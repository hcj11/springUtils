package com.example.demo;

import com.example.demo.domain.Goods;
import com.example.demo.repository.GoodsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	@Autowired
	private GoodsRepository goodsRepository;

	@Test
	public void contextLoads() {
	}
	@Test
	public void version(){
		int goodsId=1;
		Goods goods = goodsRepository.findById(goodsId).orElse(null);
		Goods goods2 = goodsRepository.findById(2).orElse(null);

		goods.setQuantity(11);
		goodsRepository.save(goods);
		goods2.setQuantity(12);
		goodsRepository.save(goods2);
		// jpa添加乐观锁，对同一行记录的修改，
//		org.springframework.orm.ObjectOptimisticLockingFailureException:
//    Object of class [com.example.demo.domain.Goods] with identifier [1]: optimistic locking failed;
//  nested exception is org.hibernate.StaleObjectStateException: Row was updated or deleted by another
//		transaction (or unsaved-value mapping was incorrect) : [com.example.demo.domain.Goods#1]

	}
	@Test
    public void test(){
	    System.out.println(" 测试终于没有问题了。  哈哈，就注意不要在安装wifi,会影响cpu的" +
                "");
	    System.out.println("10.6  没问题");
    }


}

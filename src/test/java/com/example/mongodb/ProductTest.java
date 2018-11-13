package com.example.mongodb;

import com.example.mongodb.domain.Details;
import com.example.mongodb.domain.Price;
import com.example.mongodb.domain.Product;
import com.example.mongodb.service.ProductService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by hcj on 18-8-3
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTest {

  @Autowired
  ProductService productService;

  /**
   * // 商品链接编号 private String slug; // 商品的最小单元编号 private String sku; private String name; private
   *  private Collection<String> category_ids = new LinkedHashSet<String>();
   private String main_category_id;
   private Collection<String> tags = new LinkedHashSet<String>();
   */
  @Test
  public void save() {
    ArrayList<Product> products = new ArrayList<>();
    Product product = new Product();
    product.setId("101");
    product.setSku("1232");
    product.setName("牛轧糖A型");
    product.setDescription("甜的");
    product.setDetails(new Details(11d, "g", 111d, "福派源", "blue"));
    product.setTotal_reviews(1d);
    product.setAverage_reviews(1d);
    product.setPrice(new Price(1d, 22d));
    product.getPrice_history().add(new Price(22d, 1d, Instant.now().minus(2, ChronoUnit.DAYS),
            Instant.now().minus(1, ChronoUnit.DAYS)));
        product.getPrice_history().add(new Price(444d, 44d,Instant.now().minus(10, ChronoUnit.DAYS),
            Instant.now().minus(4, ChronoUnit.DAYS)));
        // 类目id
    product.getCategory_ids().add("100");
    product.getCategory_ids().add("1001");
    product.getCategory_ids().add("1002");
    product.setMain_category_id("1002");
    product.getTags().add("菠萝");
    product.getTags().add("抹茶");
    products.add(product);

    List<Product> save = productService.save(products);
    System.out.println(save.size());
    System.out.println(save);
  }
}

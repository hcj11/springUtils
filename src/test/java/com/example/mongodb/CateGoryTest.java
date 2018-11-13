package com.example.mongodb;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import com.example.mongodb.Repository.CategoryRepository;
import com.example.mongodb.domain.Category;
import com.example.mongodb.domain.Review;
import com.example.mongodb.service.CateGoryService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.FluentMongoOperations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by hcj on 18-8-3
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CateGoryTest {

  /**
   * private String slug; private Collection<Category> ancestors = new LinkedHashSet<Category>();
   * private String parent_id; private String name; private String description;
   */
  @Autowired
  CateGoryService cateGoryService;
  @Autowired
  CategoryRepository categoryRepository;
  @Autowired
  MongoOperations mongoOperations;

  /**
   * // 先定义大分类 //    category.setName("生活用品"); //    category.setSlug("1000"); //
   * category.setDescription("生活用品的的"); //    // 若为null，则不会设置该值 //    category.setParent_id(null);
   * //    category.setAncestors(null); category.setName("糖"); category.setSlug("9222");
   * category.setDescription("糖"); category.getAncestors().add(new Category("1000",null,"生活用品","糖是天天的"));
   * category.setParent_id("5b63f49b1ed2241f4e449ea6"); category.setId("5b63f5ee1ed224233c02e262");
   * save 进行替换操作
   */
  /**更新投票信息 */
  @Test
  public void updateVote(){
    // 追加投票信息， 并更新helpful_votes ,
//    mongoOperations.updateFirst(query(Criteria.where("_id").is("105")),
//        new Update().addToSet("voter_ids","123").inc("helpful_votes",1d), Review.class);
      // ne -> voter_ids!=124 , 注意voter_ids是数组
    mongoOperations.updateFirst(query(Criteria.where("_id").is("105")
        .and("voter_ids").ne("124")),
        new Update().addToSet("voter_ids","124").
            inc("helpful_votes",1d), Review.class);

  }
  @Test
  public void updateParent(){

//    mongoOperations.updateFirst(query(Criteria.where("_id").is("1001")),update("parent_id","1004")
//        ,Category.class);
//    cateGoryService.generate("1001","1004");

    // 所有子集更新父类目
//    List<Category> categories = mongoOperations
//        .find(query(Criteria.where("parent_id").is("1004").and("_id").
//            ne("1001")), Category.class);
//    for (Category category:categories){
//      cateGoryService.generate(category.getId(),"1001");
//    }
    /**更新父数组的某一属性 */
    // 更新
    List<Category> doc = mongoOperations
        .find(query(Criteria.where("_id").is("1001")), Category.class);
    Category category = doc.get(0);
    mongoOperations.updateMulti(query(Criteria.where("ancestors._id").is("1001"))
        ,update("ancestors.$",category),Category.class);

  }

  @Test
  public void generateDemo() {
    List<Category> categories2 = new ArrayList<>();
    Category category = new Category();
    category.setName("原始糖");
    category.setSlug("9225");
    category.setDescription("现代糖的祖先"); // 糖是天天的
    category.setId("1004");
    category.setParent_id("100");
    categories2.add(category);
    List<Category> save = cateGoryService.save(categories2);
    System.out.println(save);
    cateGoryService.generate("1004","100");
  }

  @Test
  public void save() {
    List<Category> categories = new ArrayList<>();
    Category category = new Category();
    category.setName("生活用品");
    category.setSlug("1000");
    category.setDescription("生活用品的的");
    // 若为null，则不会设置该值
    category.setParent_id(null);
    category.setAncestors(null);
    category.setId("100");
    categories.add(category);
    cateGoryService.save(categories);

    List<Category> categories1 = new ArrayList<>();
    category = new Category();

    category.setName("糖");
    category.setSlug("9222");
    category.setDescription("糖");
    category.getAncestors().add(new Category("1000", null, "生活用品", "生活用品"));
    category.setParent_id("100");
    category.setId("1001");
    categories1.add(category);
    cateGoryService.save(categories1);

    List<Category> categories2 = new ArrayList<>();
    category = new Category();
    category.setName("牛轧糖");
    category.setSlug("9223");
    category.setDescription("牛轧糖"); // 糖是天天的
    category.getAncestors().add(new Category("1000", null, "生活用品", "生活用品"));
    category.getAncestors().add(new Category("9222", null, "糖", "糖是天天的"));
    category.setParent_id("101");
    category.setId("1002");
    categories2.add(category);
    List<Category> save = cateGoryService.save(categories2);
    System.out.println(save);
  }

  @Test
  public void select() {
    Category category = categoryRepository.findById("100").orElse(null);
    System.out.println(category);
  }

  @Test
  public void delete() {
    categoryRepository.deleteAll();
  }
}

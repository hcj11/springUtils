package com.example.mongodb;

import com.example.mongodb.Repository.ReviewRepository;
import com.example.mongodb.domain.Review;
import com.example.mongodb.service.ReviewService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
public class ReviewTest {

  @Autowired
  ReviewService reviewService;
  @Autowired
  ReviewRepository reviewRepository;


  /**
   * @Id private String id;
   * private String product_id;
   * private Instant date = Instant.now();
   * private String title;
   * private String text;
   * private Double rating;
   * private String user_id;
   * private String username;// 冗余 // 评分 0,1,2,3,4,5
   * private Double helpful_votes;
   * private Collection<String> voter_ids = new LinkedHashSet<>();
   */
  public void save1(){

  }

  @Test
  public void save() {
    List<Review> reviews = new ArrayList<>();
    Random random = new Random(47);
    // 0,99 100 199
    for (int i = 1; i < 6; i++) {
      Review review = new Review();
      int i1 = random.nextInt(100) + 100;
      // findAndModify
      // 另做主键， 便于索引查询,
      review.setId(String.valueOf(100+i));
      review.setProduct_id("100");
      review.setText("好处，真好吃"+i);
      review.setTitle("好处，真好吃"+i);
      review.setRating(0d+i);
      review.setUsername("login-hcj"+(i1-100));
      review.setUser_id(String.valueOf(i1));
      review.setHelpful_votes(0d+i);
      review.getVoter_ids().add(i1+"");
      int i2 = random.nextInt(100) + 100;
      review.getVoter_ids().add(i2+"");
      reviews.add(review);
    }

    reviewService.save(reviews);
  }

  @Test
  public void delete(){
    reviewRepository.deleteAll();
  }

}

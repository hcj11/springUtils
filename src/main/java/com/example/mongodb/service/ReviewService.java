package com.example.mongodb.service;

import com.example.mongodb.Repository.ReviewRepository;
import com.example.mongodb.domain.Review;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hcj on 18-8-3
 */
@Service
public class ReviewService {

  @Autowired
  ReviewRepository reviewRepository;


  public List<Review> save(Collection<Review> reviews) {

    return reviewRepository.saveAll(reviews);
  }
}

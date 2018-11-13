package com.example.mongodb.Repository;

import com.example.mongodb.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by hcj on 18-8-3
 */
public interface CategoryRepository extends MongoRepository<Category,String> {

}

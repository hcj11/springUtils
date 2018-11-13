package com.example.mongodb.Repository;

import com.example.mongodb.domain.Category;
import com.example.mongodb.domain.Product;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by hcj on 18-8-3
 */
public interface ProductRepository extends MongoRepository<Product, String> {

  List<Product> findByName(String name);

  List<Category> findByIdIn(List<String> strings);


  List<Product> findTop10ByName(String name);

  List<Product> findTop10ByNameLike(String s);
}

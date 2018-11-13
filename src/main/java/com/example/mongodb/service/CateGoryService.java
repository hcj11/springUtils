package com.example.mongodb.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import com.example.mongodb.Repository.CategoryRepository;
import com.example.mongodb.domain.Category;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * Created by hcj on 18-8-3
 */
@Service
public class CateGoryService {

  @Autowired
  CategoryRepository categoryRepository;
  @Autowired
  MongoOperations mongoOperations;

  public List<Category> save(Collection<Category> categories) {
    // 类目的冗余使用， 需要学习
    return categoryRepository.saveAll(categories);
  }
  // 根据父id，查询
  public void generate(String id ,String parent_id){
    LinkedHashSet<Category> categoryLinkedHashSet = new LinkedHashSet<>();

    List<Category> categories = mongoOperations
        .find(query(Criteria.where("_id").is(parent_id)), Category.class);

    while (categories.size()>0){
      categoryLinkedHashSet.addAll(categories);
      String parent_id1 = categories.get(0).getParent_id();
      categories = mongoOperations
          .find(query(Criteria.where("_id").is(parent_id1)), Category.class);
    }
    mongoOperations.updateFirst(query(Criteria.where("_id").is(id)),update("ancestors",categoryLinkedHashSet).set("parent_id",parent_id),Category.class);
  }
  //
//  public List<> save(){
//
//  }


}

package com.example.mongodb.Repository;

import com.example.mongodb.domain.Category;
import com.example.mongodb.domain.Details;
import com.example.mongodb.domain.Ticks;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by hcj on 18-8-3
 */
public interface TicksRepository extends MongoRepository<Ticks,Details> {

}

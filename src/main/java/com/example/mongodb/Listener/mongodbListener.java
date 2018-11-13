package com.example.mongodb.Listener;

import com.example.mongodb.domain.MongoAutoId;
import com.example.mongodb.domain.MongoId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ReflectionUtils;

/**
 * Created by hcj on 18-8-5
 */
@Configuration
public class mongodbListener extends AbstractMongoEventListener<Object> {

  @Autowired
  MongoTemplate mongoTemplate;
  //
  final Object lock = new Object();

  @Override
  public void onBeforeConvert(final BeforeConvertEvent<Object> event) {
    System.out.println(event);
    // 只为doInsert 方法设计
    final Object source = event.getSource();

    if (source != null) {
      ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {
        @Override
        public void doWith(final java.lang.reflect.Field field)
            throws IllegalArgumentException, IllegalAccessException {
          ReflectionUtils.makeAccessible(field);
          // 根据注解判断参数
          if (field.isAnnotationPresent(MongoAutoId.class) && field.get(source) == null) {

            field.set(source, getId(event.getCollectionName()));
          }
        }
      });

    }

//    ReflectionUtils.doWithMethods();
  }

  // synchronized 保持原子性？
  public  Integer getId(final String collName) {
    // 提前计算, 根据
    // 查询目前的大小 , 或者其他锁,
    // 添加事务锁, 解决没有事务的问题，
//      Aggregation agg = newAggregation(
//          project("_id")
//          , sort(Direction.DESC, "_id")
//          , limit(1)
//      );
//      AggregationResults<Map> results = mongoTemplate.aggregate(agg, collName, Map.class);
//      Integer id = (Integer) results.getMappedResults().get(0).get("_id");
//      id = id + 1;
//      return id;
    final Query query = new Query().addCriteria(
        new Criteria(MongoId.FIELD_COLLNAME).is(collName));
    final Update update = new Update();
    update.inc(MongoId.FIELD_SEQID, 1);
    final FindAndModifyOptions options = new FindAndModifyOptions().upsert(true).returnNew
        (true);
    final MongoId sequence = mongoTemplate.findAndModify(query, update, options,
        MongoId.class);
    return sequence.getSeqId();
  }

  // 并发增加
//    User andModify = mongoTemplate.findAndModify(query(Criteria.where("_id").is(id))
//        , new Update().inc("_id", 1), new FindAndModifyOptions().
//            returnNew(true).upsert(true)
//        , source.getClass());

//    mongoTemplate.findAndModify(
//        query(Criteria.where("_id"))
//    )


  public static void main(String[] args) {
    String collName = "order";

    System.out.println(collName.getClass());
  }


}

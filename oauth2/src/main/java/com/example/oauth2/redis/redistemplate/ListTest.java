//package com.example.oauth2.redis.redistemplate;
//
//import com.example.redisdemo.domain.MessgeInfo;
//import com.example.redisdemo.util.RandomUtil;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Set;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.dao.DataAccessException;
//import org.springframework.data.redis.connection.RedisConnection;
//import org.springframework.data.redis.core.ListOperations;
//import org.springframework.data.redis.core.RedisCallback;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
///**
// * byte[] <-> string Created by hcj on 18-7-25
// */
//@Configuration
//public class ListTest implements InitializingBean {
//
//  private final String MessgeInfoSTR = "MessgeInfolist:";
//  private final int timeout = 60 * 60 * 24 * 30;
//
//  @Autowired
//  @Qualifier("redisTemplate")
//  private RedisTemplate<String, Object> redisTemplate;
//  @Autowired
//  @Qualifier("redisTemplateX")
//  private RedisTemplate<String, Object> redisTemplateX;
//  private final static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//  private final static JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
//  private ListOperations<String, Object> listOperations;
//
//
//  public void afterPropertiesSet() throws Exception {
//    // bean以实例话， 并进行了属性填充
//    listOperations = redisTemplate.opsForList();
//
//  }
//
//
//  public void addMessageWithPipeline(List<MessgeInfo> MessgeInfos) {
//    String s = MessgeInfoSTR + RandomUtil.generateActivationKey();
//    // MessgeInfolist:1111
//    byte[] serialize = stringRedisSerializer.serialize(s);
//
//    RedisCallback<List<Object>> redisCallback = new RedisCallback<List<Object>>() {
//
//      public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
//        connection.openPipeline();
//        for (MessgeInfo messgeInfo : MessgeInfos) {
//          byte[] serialize1 = jdkSerializationRedisSerializer.serialize(messgeInfo);
//           connection.lPush(serialize, serialize1);
//
//        }
//        return connection.closePipeline();
//      }
//    };
//    List<Object> execute = redisTemplate.execute(redisCallback, false, true);
//    for (Object object : execute) {
//      System.out.println(object.toString());
//    }
//
//  }
//
//  // 根据key查询所有value
//  public List<List<MessgeInfo>> selectMessgeInfoWithCollection(Collection<String> keys) {
//    // 获取某个key的数据，开管道
//
////    Long size = listOperations.size(((ArrayList<String>) keys).get(0));
////    System.out.println(size);
//
//    RedisCallback<Object> redisCallback = new RedisCallback<Object>() {
//      @Override
//      public Object doInRedis(RedisConnection connection) throws DataAccessException {
//        connection.openPipeline();
//        for (String key:keys){
//              byte[] serialize = stringRedisSerializer.serialize(key);
//
//            // todo 貌似获取不到 0 ,-1
////          Long aLong = connection.lLen(serialize);
////          System.out.println(aLong);
//          connection.listCommands().lRange(serialize, 0, -1);
//        }
//        return connection.closePipeline();
//      }
//    };
//    // desriable
//    List<Object> execute = ( List<Object>) redisTemplate.execute(redisCallback, false, true);
//    List<List<MessgeInfo>> objects2 = new ArrayList<>();
//    List<MessgeInfo> objects = new ArrayList<>();
//    for (Object object : execute) {
//      List<Object> object1 = (List<Object>) object;
//      for (Object objectTmp:object1){
//        MessgeInfo deserialize = (MessgeInfo) jdkSerializationRedisSerializer.deserialize((byte[]) objectTmp);
//        objects.add(deserialize);
//      }
//      objects2.add(objects);
//    }
//    return objects2;
//  }
//
//  public Set<String> selectMessgeInfoListWithPartten(String key) {
//    // todo 获取对应表达式的所有的key
//    Set<String> keys = listOperations.getOperations().keys(key + "*");
////    System.out.println(keys);
//    return keys;
//  }
//  public void updateMessgeInfo(List<MessgeInfo> MessgeInfos) {
//    // 若有对应关系就是map
//    Set<String> strings = selectMessgeInfoListWithPartten(MessgeInfoSTR);
//    // 对一个key进行替换，
//    LinkedList<String> strings1 = new LinkedList<>(strings);
//    // todo 不过期
//    RedisCallback<List<Object>> redisCallback = new RedisCallback<List<Object>>() {
//      public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
//        int count=0;
//        connection.openPipeline();
//        String pop = strings1.pop();
//        byte[] serialize = stringRedisSerializer.serialize(pop);
//        for (MessgeInfo messgeInfo : MessgeInfos) {
//          ++count;
//          byte[] serialize1 = jdkSerializationRedisSerializer.serialize(messgeInfo);
//          // 序列化？
//          connection.listCommands().lSet(serialize,count-1,serialize1);
//        }
//        return connection.closePipeline();
//      }
//    };
//    List<Object> execute = redisTemplate.execute(redisCallback, false, true);
//    for (Object object : execute) {
//      System.out.println(object.toString());
//    }
//  }
//  public void remove(){
//    listOperations.remove("",1,"");
//  }
//
//  // 修剪长度
//  public void demo(){
//    String key="MessgeInfolist:12";
//    // 返回列表中索引i 对应的 元素
//    System.out.println(listOperations.rightPush(key,"123"));
//    System.out.println(listOperations.rightPush(key,"321"));
//    for (int i=0;i<10;i++){
//      System.out.println(listOperations.leftPush(key,i+""));
//    }
//    System.out.println(listOperations.rightPop(key));
//    System.out.println(listOperations.leftPop(key));
//    System.out.println(listOperations.index(key,1));
//    listOperations.trim(key,0,5);
//  }
//
//
//  public void deleteMessgeInfos(Collection<String> keys) {
//    // 若删除某一些key的话可以用管道
////    listOperations.remove();
////    new RedisCallback<>() {
////      @Override
////      public Object doInRedis(RedisConnection connection) throws DataAccessException {
////    // 删除值为。。 的数量，
////        connection.listCommands().lRem()
////        return null;
////      }
////    }
////    Long delete = listOperations.getOperations().delete(keys);
////    System.out.println("删除成功：" + delete);
//  }
//
//
//  public void deleteMessgeInfo(String id) {
//    Boolean delete = listOperations.getOperations().delete(id);
//    if (delete) {
//      System.out.println("删除成功：" + id);
//    }
//  }
//
//
//}

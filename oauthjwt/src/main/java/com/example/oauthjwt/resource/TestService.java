package com.example.oauthjwt.resource;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by hcj on 18-8-11
 */
@Service
public class TestService {

  @Autowired
  TestRepository testRepository;

  public List<TestVO> select(TestQueryVO testVM) {
    //  根据条件查询所有
    Pageable of = PageRequest.of(testVM.getPage()-1, testVM.getPageSize());
    List<test> content = testRepository.findAll(of).getContent();
    // 不起作用，
    List<TestVO> testVOList = Lists.newArrayList();

    // string ? => arrays
    BeanUtils.copyProperties(content, testVOList);

    return testVOList;
  }

  public long count() {
    return testRepository.count();
  }

  public void save(TestVO vo) {

    test test1 = new test();
    // 静态构建
//    test build = test.builder().desc("").build();


    test test = new test();

    BeanUtils.copyProperties(vo,test);
    // 修改
    test.setInterest(vo.getInterest().toString());
    test.setGood(vo.getGood().toString());
    if(vo.getXxday()!=null){
      LocalDateTime xxday = LocalDateTime.from(Constants.dateTimeFormatter_ACROSS.parse(vo.getXxday()));
      test.setXxday(xxday);
    }
    if(vo.getBirthday()!=null){
      LocalDateTime birthday = LocalDateTime.from(Constants.dateTimeFormatter_ACROSS.parse(vo.getBirthday()));
      test.setBirthday(birthday);
    }


    testRepository.save(test);
  }

  public static void main(String[] args) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime from = LocalDateTime.from(dateTimeFormatter.parse("2016-01-01 12:34:56"));
    System.out.println(from);

  }

}

//package com.example.oauthjwt.resource;
//
//import com.example.oauthjwt.resource.test;
//import com.example.oauthjwt.resource.SuccessVm;
//import com.example.oauthjwt.resource.TestService;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * Created by hcj on 18-8-11
// */
//@RestController
//@RequestMapping("/test")
//public class testResource {
//
//  @Autowired
//  TestService testService;
//
//  @PostMapping("/select")
//  public ResponseEntity select(@RequestBody TestVM testVM) {
//
//    List<test> tests = testService.select(testVM);
//    String count = String.valueOf(testService.count());
//
//    SuccessVm<List> listSuccessVm = new SuccessVm<List>("", Integer.parseInt(count), tests);
//    return ResponseEntity.ok(listSuccessVm);
//  }
//
//  @PostMapping
//  public void insert(test test){
//    testService.save(test);
//  }
//
////  @PostMapping
////  public void insert(test test){
////    testService.save(test);
////  }
//
//}

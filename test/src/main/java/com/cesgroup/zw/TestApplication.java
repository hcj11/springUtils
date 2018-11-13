package com.cesgroup.zw;

import com.cesgroup.zw.auth.entity.User;
import com.cesgroup.zw.auth.service.IUserService;
import com.cesgroup.zw.framework.base.bo.UserDetail;
import com.cesgroup.zw.framework.base.web.controller.BaseServiceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.List;

@SpringBootApplication
//@MapperScan(basePackages = {"com.cesgroup.zw.auth.dao","com.cesgroup.module.test.dao"})
//@MapperScan(basePackages = "com.cesgroupp")
public class TestApplication  {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TestApplication.class, args);
        IUserService bean = context.getBean(IUserService.class);
//        System.out.println(bean!=null);
        User user = bean.selectUserById("2a55baae964f46a3802585810fcbfd70");
//        List<User> users = bean.queryAllUser();
        System.out.println(user);
    }
}

package com.cesgroup.zw;

import com.cesgroup.zw.auth.entity.User;
import com.cesgroup.zw.auth.service.IUserService;
import com.cesgroup.zw.auth.service.impl.UserSeviceImpl;
import com.cesgroup.zw.module.test.service.impl.ActionServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class TestApplicationTests {
    @Autowired
    ActionServiceImpl actionService;

    @Autowired
    UserSeviceImpl userService;


    @Test
    public void contextLoads() {
//        actionService.get();

//        List<User> users = userService.queryAllUser();
//        System.out.println(users);
        User user =   userService.selectUserById("2a55baae964f46a3802585810fcbfd70");
        System.out.println(user);

    }

}

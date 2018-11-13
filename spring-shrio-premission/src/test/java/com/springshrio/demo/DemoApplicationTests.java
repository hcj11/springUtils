package com.springshrio.demo;

import org.assertj.core.util.Lists;
import org.hibernate.secure.spi.PermissibleAction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import premssion.PremssionApplication;
import premssion.from.SysUserFrom;
import premssion.model.SysRole;
import premssion.model.SysUser;
import premssion.service.SysUserService;
import premssion.service.impl.SysUserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PremssionApplication.class)
public class DemoApplicationTests {
    @Autowired
    SysUserService userService;


    @Test
    public void contextLoads() {
        SysRole build = SysRole.builder().id(7).build();
        SysUserFrom admin = SysUserFrom.builder().
                account("admin").name("admin").password("123456").forbidden("0")
                .sysRoles(Lists.newArrayList(build))
                .build();

        userService.saveUser(admin);
    }

}

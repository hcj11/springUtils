package com.cesgroup.zw.module.test.controller;

import com.cesgroup.zw.auth.bo.CustomUserDetail;
import com.cesgroup.zw.auth.entity.User;
import com.cesgroup.zw.auth.service.IUserService;
import com.cesgroup.zw.auth.service.impl.UserSeviceImpl;
import com.cesgroup.zw.framework.base.bo.UserDetail;
import com.cesgroup.zw.framework.base.web.annotations.ResponseResult;
import com.cesgroup.zw.framework.base.web.controller.BaseServiceController;
import com.cesgroup.zw.module.test.entity.Action;
import com.cesgroup.zw.module.test.service.ActionService;
import com.cesgroup.zw.module.test.service.impl.ActionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ResponseResult
public class ActionController extends BaseServiceController<Action, String, ActionServiceImpl> {
    @Autowired
    ActionService actionService;
    @Autowired
    UserSeviceImpl userService;

    // users -> 已关联到用户
    @GetMapping(value = "/user")
    public void get(CustomUserDetail userDetail) {
        System.out.println(userDetail);
        userDetail.getUserId();

        List<User> users = userService.queryAllUser();
        System.out.println(users);
    }

    @GetMapping(value = "/actions")
    public List<User> get2() {
        actionService.get();
        List<User> users = userService.queryAllUser();
        return users;
    }

//    @GetMapping(value = "/action")
//    public void get3(@RequestBody UserCondition  action,CustomUserDetail userDetail) {
//        String name = userDetail.getName();
//        return getBaseService().findPage(cond);
//
//    }


}

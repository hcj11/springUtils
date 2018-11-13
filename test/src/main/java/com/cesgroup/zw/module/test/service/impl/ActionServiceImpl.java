package com.cesgroup.zw.module.test.service.impl;

import com.cesgroup.zw.module.test.dao.ActionMapper;
import com.cesgroup.zw.module.test.entity.Action;
import com.cesgroup.zw.module.test.service.ActionService;
import com.cesgroup.zw.auth.service.IUserService;
import com.cesgroup.zw.framework.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ActionServiceImpl extends BaseServiceImpl<Action, String, ActionMapper> implements ActionService {
//    @Autowired
//    IUserService userService;

    public void get() {
        List<Action> actions1 = super.getDao().selectAll();
        System.out.println(actions1);
    }
}

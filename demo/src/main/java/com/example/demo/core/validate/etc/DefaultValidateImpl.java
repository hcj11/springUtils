package com.example.demo.core.validate.etc;

import com.example.demo.core.validate.Error;
import com.example.demo.core.validate.Result;
import com.example.demo.core.validate.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * 统一校验公共类
 *  Create by houchunjian on 2018/10/23 0023
 */
public class DefaultValidateImpl {
    /**
     * 方法注入
     *  Create by houchunjian on 2018/10/23 0023
     */
    List<Validator> validators; // 实现validator的接口的类

    public DefaultValidateImpl(List<Validator> validators) {
        this.validators = validators;
    }

    public List<Error> validate(String text) {
        List<Error> errors = new ArrayList<>();
        // 添加接口注入到此类中，
        // 扫包动态代理， 生成该接口的实现类 并注册到spring容器中，
        for (Validator validator : validators) {
            Result result = validator.validate(text);
            if (result.isError) {
                errors.add(result.getError());
            }
            return errors;
        }
        return null;
    }
}

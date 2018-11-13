package com.example.demo.core.validate.impl;

import com.example.demo.core.validate.Error;
import com.example.demo.core.validate.Result;
import com.example.demo.core.validate.Validator;
import org.apache.commons.lang3.StringUtils;

public class LengthValidator implements Validator {
    @Override
    public boolean isValidator() {
        Class<? extends LengthValidator> cl = this.getClass();
        if (cl.getInterfaces().length == 1 && cl.getInterfaces()[0].equals(Validator.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Result validate(String text) {
        if (StringUtils.isNotEmpty(text) && text.length() > 5) {
            Result result = new Result();
            result.isError = true;
            result.setError(new Error("参数长度不合法. 大于5"));
            return result;
        } else {
            Result result = new Result();
            result.isError = false;
            return result;
        }


    }
}
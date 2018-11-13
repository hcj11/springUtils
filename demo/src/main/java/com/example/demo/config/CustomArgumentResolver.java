package com.example.demo.config;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

import javax.servlet.ServletException;

/**
 * 自定义参数解析器
 * Created by houchunjian on 2018/9/21 0021
 */
@Component
public class CustomArgumentResolver extends AbstractNamedValueMethodArgumentResolver {
    @Autowired
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CustomArg.class);
    }

    @Override
    protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
        CustomArg ann = parameter.getParameterAnnotation(CustomArg.class);
        Assert.state(ann != null, "No CustomArg annotation");
        return new NamedValueInfo(ann.name(), ann.required(), ValueConstants.DEFAULT_NONE);
    }

    @Override
    @Nullable
    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) {
        // 从headers中获取 值
        return userService.findOne(1L);
//        System.out.println(name);
//        System.out.println(parameter);
//        System.out.println(request.getParameterNames());
//        System.out.println(request.getUserPrincipal());
//        //
//
//        return User.builder().login("侯").password("123").build();
    }

    @Override
    protected void handleMissingValue(String name, MethodParameter parameter) throws ServletException {
        throw new ServletRequestBindingException("Missing CustomArg attribute '" + name +
                "' of type " + parameter.getNestedParameterType().getSimpleName());
    }

}

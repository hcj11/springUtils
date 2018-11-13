package com.example.demo;

import com.example.demo.core.validate.Error;
import com.example.demo.core.validate.etc.DefaultValidateImpl;
import org.springframework.context.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 只是@ComponentScan
 * Create by houchunjian on 2018/10/23 0023
 */
@ComponentScan(value = {"com.example.demo.core.validate.impl","com.example.demo.core.validate.etc"},
        useDefaultFilters = false, includeFilters = {
        @ComponentScan.Filter(type = FilterType.CUSTOM, value = CustomTypeFilter.class)})
public class Config {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        System.out.println("-----------------------");
        print(context.getBeanDefinitionNames());

        DefaultValidateImpl bean = (DefaultValidateImpl) context.getBean("defaultValidateImpl");
        List<Error> lists = bean.validate("大大撒大大大撒大大");
        print(lists);

    }

    private static void print(List<Error> lists) {
        lists.forEach(System.out::println);
    }

    public static void print(String[] beanNames) {

        Arrays.asList(beanNames).forEach(System.out::println);
    }

}
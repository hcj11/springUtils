package com.example.demo;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;

abstract class C<T> {
    public abstract T id(T idx);

}

public class D extends C {
    public String id(String idx) {
        return idx;
    }

    //     设置桥方法
    public Object id(Object idx) {
        return id((String) idx);
    }


    public static void main(String[] args) {
        //
//        Object o = new Object();
//        String o1 = (String) o;

        C c = new D();
        Method[] methods = c.getClass().getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }

        System.out.println(c.id(new Object()));
        System.out.println(c.id(new String("123")));
        ;
    }

}


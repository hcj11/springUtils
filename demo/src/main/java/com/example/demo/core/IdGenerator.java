package com.example.demo.core;

/**
 * id生成接口
 * 
 * @author 国栋
 *
 */
public interface IdGenerator  {

    /**
     * 生成id
     */
    long generateId();

    /**
     * 生成id
     */
    long generateId(String name);

    /**
     * 生成id
     */
    long generateId(Class<?> clz);
    String getNextId();
}

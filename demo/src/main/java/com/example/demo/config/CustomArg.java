package com.example.demo.config;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(value = ElementType.PARAMETER)
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface CustomArg {
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    boolean required() default false;
}

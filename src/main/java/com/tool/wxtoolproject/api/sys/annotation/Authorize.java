package com.tool.wxtoolproject.api.sys.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Yangke
 * @Date: 2019/3/28 19:57
 *authorize 是判断 是否需要 token
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {
    boolean required() default true;
}


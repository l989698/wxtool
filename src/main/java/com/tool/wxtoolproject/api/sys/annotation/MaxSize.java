package com.tool.wxtoolproject.api.sys.annotation;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface MaxSize {
    public int max() default 20;

    public String message() default "长度太长";
}
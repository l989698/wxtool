package com.tool.wxtoolproject.api.sys.annotation;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MinSize {
    public int min() default 0;

    public String message() default "长度太短";
}
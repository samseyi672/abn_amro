package com.abn_amro.usermanagment.config;


import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogRequestResponse {
    boolean logRequestBody() default true;
    boolean logResponseBody() default true;
    String[] excludeFields() default {};
}



package com.abn_amro.recipemanagement.config;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogRequestResponse {
    boolean logRequestBody() default true;
    boolean logResponseBody() default true;
    String[] excludeFields() default {};
}



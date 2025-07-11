package com.abn_amro.recipemanagement.config;


import com.abn_amro.recipemanagement.serviceImpl.MailServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * logger.
 * <p>
 * this class handles logging across controllers for support
 * </p>
 *
 * @author Sam
 * @since 2025
 */
@Component
@Aspect
public class LogConfiguration {

    private final static Logger log = LoggerFactory.getLogger(MailServiceImpl.class);
    private final HttpServletRequest request;
    private final ObjectMapper objectMapper;

    public LogConfiguration(HttpServletRequest request, ObjectMapper objectMapper) {
        this.request = request;
        this.objectMapper = objectMapper;
    }

    @Around("@within(LogRequestResponse) || @annotation(LogRequestResponse)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> declaringClass = signature.getDeclaringType();

        LogRequestResponse annotation = method.getAnnotation(LogRequestResponse.class);
        if (annotation == null) {
            annotation = declaringClass.getAnnotation(LogRequestResponse.class);
        }

        if (annotation == null) {
            return joinPoint.proceed(); // fallback, shouldn't happen
        }

        // Same logic from before...
        if (annotation.logRequestBody()) {
            Object[] args = joinPoint.getArgs();
            List<Object> filtered = new ArrayList<>();
            for (Object arg : args) {
                filtered.add(filterFields(arg, annotation.excludeFields()));
            }
            log.info("Request from IP: " + request.getRemoteAddr());
            log.info("Request body: " + objectMapper.writeValueAsString(filtered));
        }

        Object result = joinPoint.proceed();

        if (annotation.logResponseBody() && result instanceof ResponseEntity<?>) {
            ResponseEntity<?> response = (ResponseEntity<?>) result;
            log.info("Response: " + objectMapper.writeValueAsString(response.getBody()));
        }

        return result;
    }

    private Object filterFields(Object arg, String[] excludeFields) {
        try {
            if (arg == null) return null;

            Map<String, Object> map = objectMapper.convertValue(arg, Map.class);
            for (String field : excludeFields) {
                map.remove(field);
            }
            return map;
        } catch (Exception e) {
            return arg;
        }
    }
}
































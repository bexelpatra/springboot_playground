package com.example.demo.config;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Keygen implements KeyGenerator {
    
    @Override
    public Object generate(Object target, Method method, Object... params) {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();
        sb.append(method.getName()).append(":");
        for (Object object : params) {
            sb.append(object).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        log.info("key id : {}",sb.toString());
        
        return sb.toString();
    }
    
    
}

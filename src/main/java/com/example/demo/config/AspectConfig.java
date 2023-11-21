package com.example.demo.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@Aspect
public class AspectConfig {
    @Around("execution(* com.example.demo.repository..*(..))")
    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable{
        long begin = System.currentTimeMillis(); 
        
        Object retVal = pjp.proceed(); // 메서드 호출 자체를 감쌈 

        System.out.println(System.currentTimeMillis() - begin); 
        return retVal; 
    }
}

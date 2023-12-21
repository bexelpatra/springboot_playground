package com.example.demo.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableAspectJAutoProxy
@Aspect
@Slf4j
public class AspectConfig {
    @Around("execution(* com.example.demo..*(..))")
    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable{
        long begin = System.currentTimeMillis(); 
        log.info("{}",pjp.toLongString());  
        Object retVal= null;
        try {
            retVal = pjp.proceed(); // 메서드 호출 자체를 감쌈 
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        // System.out.printf("time is %d\n",System.currentTimeMillis() - begin); 
        return retVal; 
    }
}

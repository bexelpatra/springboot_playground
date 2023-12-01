package com.example.demo.test_proxy;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class ServiceFilter implements MethodInterceptor{
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        // TODO Auto-generated method stub
        Object result = null;
        System.out.println("ServiceFilter");
        result= proxy.invokeSuper(obj, args);
        
        return result;
    }
}

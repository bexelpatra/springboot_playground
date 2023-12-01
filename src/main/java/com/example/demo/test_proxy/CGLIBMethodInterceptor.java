package com.example.demo.test_proxy;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class CGLIBMethodInterceptor implements MethodInterceptor{

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        // TODO Auto-generated method stub
        Object result = null;
        System.out.println("this is CGLIBMethodInterCeptor !!");
        System.out.println(method.getName());
        // method.invoke() 또는 proxy.invoke() 사용시 무한 루프에 빠진다.
        result = proxy.invokeSuper(obj, args);
        return result;
    }
    
}

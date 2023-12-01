package com.example.demo.test_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class JDKInvokeHandler implements InvocationHandler{
    private Object target;
    public JDKInvokeHandler(Object target) {
        this.target = target;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO Auto-generated method stub
        Object result = null;
        System.out.println("this is JDKInvocation Handler!!");
        System.out.printf("++ %s started !!! \n",method.getName());
        result = method.invoke(target, args);
        System.out.printf("-- %s end !!! \n",method.getName());
        return result;
    }
    
}

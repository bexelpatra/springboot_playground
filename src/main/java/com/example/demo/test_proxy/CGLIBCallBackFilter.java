package com.example.demo.test_proxy;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.CallbackFilter;

public class CGLIBCallBackFilter implements CallbackFilter{
    @Override
    public int accept(Method method) {
        // TODO Auto-generated method stub
        String name = method.getName().toLowerCase();
        if(name.endsWith("service")){
            return 0;
        }else if(name.endsWith("dao")){
            return 1;
        }
        return 0;
    }
}

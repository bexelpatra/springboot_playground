package com.example.demo.utils;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
public class Util1 {
    private final MyProperties myProperties;

    public Util1(MyProperties myProperties) {
        this.myProperties = myProperties;
    }
    // 외부 프로퍼티 읽어오기
    public void a(){        
        System.out.println(this.myProperties.getA());
        System.out.println(new String(this.myProperties.getArr()));
    }

    
}

package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@PropertySource("classpath:test.properties")
@Component
@Data
public class MyProperties {
    @Value("${arr}")
    private byte[] arr;
    @Value("${a}")
    private String a;
}

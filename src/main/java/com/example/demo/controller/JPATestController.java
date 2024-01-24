package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.DSLService;

import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(path = "/jpa")
@RequiredArgsConstructor
public class JPATestController {

    private final DSLService query;

    @PostMapping(path = "/test1")
	public ResponseEntity<Map<String,Object>> test1(ServletRequest request,@RequestBody Map<String,Object> map) {
        query.test(map.getOrDefault("a","").toString());
		return ResponseEntity.ok(map);  
	}
}

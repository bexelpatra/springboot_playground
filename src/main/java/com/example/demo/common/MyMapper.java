package com.example.demo.common;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Getter
public class MyMapper {
	private final ObjectMapper objectMapper;
	
}

package com.example.demo;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.repository.SpringTestRepository;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
class MySpringApplicationTests {

	private final SpringTestRepository springTestRepository;
	@Test
	void contextLoads() {
		
	}

}

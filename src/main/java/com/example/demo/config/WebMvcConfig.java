package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.common.MyInterceptor;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class WebMvcConfig  implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		log.info("");
		registry
		.addInterceptor(new MyInterceptor())
		.addPathPatterns("/**")
		;
	}
	
}

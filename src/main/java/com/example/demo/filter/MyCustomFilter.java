package com.example.demo.filter;

import java.io.IOException;

import com.example.demo.common.JwtTokenProvider;
import com.example.demo.common.MyMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class MyCustomFilter extends GenericFilter{
	private final MyMapper mapper;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.info("");
		// TODO Auto-generated method stub
		request.setAttribute("om", mapper);
		chain.doFilter(request, response);
	}

}

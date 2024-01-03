package com.example.demo.filter;

import java.io.IOException;

import com.example.demo.common.MyMapper;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 디스패처 앞에서 업무를 처리하기 위해서 필요하다.
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
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println();
			response.getWriter().write(new JwtException("넌 그래서 안되는 거야", e).getMessage());
		}
	}

}

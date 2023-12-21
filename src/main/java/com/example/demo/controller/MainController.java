package com.example.demo.controller;

import java.beans.beancontext.BeanContext;
import java.beans.beancontext.BeanContextSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.naming.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.PropertySource;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.JwtTokenProvider;
import com.example.demo.dto.TokenInfo;
import com.example.demo.entity.SpringTestEntity;
import com.example.demo.entity.compositeKey.SpringTestEntityKey;
import com.example.demo.repository.SpringTestRepository;
import com.example.demo.service.MyServiceImpl;
import com.example.demo.utils.Util1;

import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(path = "/main")
@RequiredArgsConstructor
@PropertySource("classpath:test.properties")
public class MainController {

	private final SpringTestRepository springTestRepository;
	private final CacheManager cacheManager;
	private final MyServiceImpl myServiceImpl;
	private final JwtTokenProvider jwtTokenProvider;

	private final Util1 util;
	
	@PostMapping(path = "/test1")
	public ResponseEntity<Map<String,Object>> test1(ServletRequest request,@RequestBody Map<String,Object> map) {
		util.a();
		
		StringBuilder sb = new StringBuilder();
		sb.append("parameter check ");


		map.forEach((t, u) -> sb.append(t).append(":").append(u));
		log.info(sb.toString());
		SpringTestEntityKey key = SpringTestEntityKey.builder()
									.testA(map.getOrDefault("testA", "").toString())
									.build();

		SpringTestEntity springTestEntity = SpringTestEntity.builder().key(key).build();

		List<SpringTestEntity> entt= springTestRepository.findAllByTestA(springTestEntity);

		System.out.println("=========================");
		System.out.println("쿼리 결과 출력");

		entt.stream()
		.forEach(t ->	System.out.println(" => "+t.toString()));
		// entt.stream().forEach(System.out::println);

		Optional<String> op = Optional.of("null");
		System.out.println("쿼리 결과 출력 끝");
		System.out.println("=========================\n");

		map.put("data", entt);
		System.out.println("\tquery 결과 끝");
		
		if(map.getOrDefault("reset","").equals("reset")){
			log.info("=======================================================");
			log.info("캐시 초기화");
			Collection<String> list = cacheManager.getCacheNames();	
			list.stream().map(t -> (Map<String,Object>)cacheManager.getCache(t).getNativeCache()).forEach(t -> t.clear());;
			log.info("=======================================================");
		}
		cacheAll("캐시 출력");
		return ResponseEntity.ok(map);  
	}
	@PostMapping(path = "/genToken")
	public ResponseEntity<Map<String,Object>> genToken(ServletRequest request,@RequestBody Map<String,Object> map) {
		Map<String,Object> result = new HashMap<>();
		String id = map.getOrDefault("id", "-1").toString();
		List<String> list = (ArrayList) map.get("auth");
		TokenInfo tokenInfo = jwtTokenProvider.generateToken(id, list.toArray(new String[list.size()]));
		result.put(id, tokenInfo);
		return ResponseEntity.ok(result);
	}
	@PostMapping(path = "/check")
	public ResponseEntity<Map<String,Object>> check(ServletRequest request,@RequestBody Map<String,Object> map) {
		Map<String,Object> result = new HashMap<>();
		log.info("들어옴");
		return ResponseEntity.ok(result);
	}
	

	public void cacheAll(String name){
		System.out.println(name);

		Collection<String> list = cacheManager.getCacheNames();
		StringBuilder sb = new StringBuilder();
		for (String cacheName : list) {
            Cache cache = cacheManager.getCache(cacheName);
			sb.append(cacheName).append("\n");
            if (cache != null) {
                Map<String,Object> nativeCache = (Map<String,Object>) cache.getNativeCache();
				nativeCache.forEach((t, u) -> {
					sb.append("\t").append(t + " : " + u + " "+ u.getClass().getName());
				});
            }
        }
		System.out.println(sb.toString());
	}
	//https://docs.spring.io/spring-framework/reference/core/expressions/language-ref/operator-elvis.html
	public static void main(String[] args) {
		ExpressionParser parser = new SpelExpressionParser();
		// Expression expression = parser.parseExpression("new Date().getTime()");
		
		EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();
		context.setVariable("newName", "Mike Tesla");

		// Expression expression = parser.parseExpression("new java.util.Date()");
		Expression expression = parser.parseExpression("#newName");
		System.out.println(expression.getValue());
		// Integer value = expression.getValue(Integer.class);
		// System.out.println(value);
		
	}
}

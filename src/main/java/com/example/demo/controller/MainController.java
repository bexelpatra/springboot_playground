package com.example.demo.controller;

import java.beans.beancontext.BeanContext;
import java.beans.beancontext.BeanContextSupport;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.naming.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.stream.Record;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
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
import com.example.demo.utils.RedisUtil;
import com.example.demo.utils.Util1;

import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(path = "/main")
@RequiredArgsConstructor
public class MainController {

	private final SpringTestRepository springTestRepository;
	private final CacheManager cacheManager;
	private final MyServiceImpl myServiceImpl;
	private final JwtTokenProvider jwtTokenProvider;

	private final RedisTemplate<String,String> redisTemplate;
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
	@Autowired
	private RedisUtil redisUtil;
	@PostMapping(path = "/redis")
	public ResponseEntity<Map<String,Object>> redis(ServletRequest request,@RequestBody Map<String,Object> map) {
		Map<String,Object> result = new HashMap<>();
		String id = map.getOrDefault("id", "-1").toString();
		for (int i = 0; i < 4; i++) {
			redisTemplate.opsForValue().set("id"+i, String.valueOf(i));
		}
		// redisTemplate.opsForValue().set("bomb", "100", 10, TimeUnit.SECONDS);
		// redisTemplate.opsForValue().getAndExpire("id2", 5,TimeUnit.SECONDS);
		
		Map<String,String> newMap = new HashMap<>();
		map.keySet().forEach(t -> newMap.put(t, map.get(t).toString()));
		redisUtil.stream1(newMap);
		return ResponseEntity.ok(result);
	}

	@PostMapping(path = "/redis/stream")
	public ResponseEntity<Map<String,Object>> redisStream(ServletRequest request,@RequestBody Map<String,Object> map) {
		Map<String,Object> result = new HashMap<>();
		String id = map.getOrDefault("id", "-1").toString();
		StreamOperations<String, String, String> opsForStream = redisTemplate.opsForStream();
		int sec = 5;
		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime next = now.plus(sec, ChronoUnit.SECONDS);
		Map<String,String> rec = new HashMap<>();
		int count  =0;
		while(next.toEpochSecond() - ZonedDateTime.now().toEpochSecond() >0){

			rec.put("timeStamp", String.valueOf(new Date().getTime()));
			rec.put("key1", String.valueOf((int)(Math.random()*1000)));
			// opsForStream.add(Record.of(rec));
			opsForStream.add("123", rec);
			count +=1;
		}
		log.info("#################### {} #####################",count);
		
		return ResponseEntity.ok(result);
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

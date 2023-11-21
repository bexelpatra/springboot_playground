package com.example.demo.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.SpringTestEntity;
import com.example.demo.entity.compositeKey.SpringTestEntityKey;
import com.example.demo.repository.SpringTestRepository;

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
	@PostMapping(path = "/test1")
	public ResponseEntity<Map<String,Object>> test1(ServletRequest request,@RequestBody Map<String,Object> map) {
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
}

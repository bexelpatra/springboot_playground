package com.example.demo.controller;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.internals.Topic;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.connection.stream.StreamReadOptions;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(path = "/redis")
@RequiredArgsConstructor
public class RedisController {
    private final RedisTemplate<String,String> redisTemplate;
    private final RedisMessageListenerContainer redisMessageListenerContainer;

	@PostMapping(path = "/receive")
    @SuppressWarnings("unchecked")
	public ResponseEntity<Map<String,Object>> receive(ServletRequest request,@RequestBody Map<String,Object> map) {
		StreamOperations<String, String, String> opsForStream = redisTemplate.opsForStream();
        ZonedDateTime end = ZonedDateTime.now().plus(5, ChronoUnit.SECONDS);
        String key = String.valueOf(map.getOrDefault("key","key"));
        String group = String.valueOf(map.getOrDefault("group","group"));
        String consumer = String.valueOf(map.getOrDefault("consumer","consumer"));
        StreamReadOptions options = StreamReadOptions.empty().count(10).block(Duration.ofSeconds(10l));
        while(end.toEpochSecond()-ZonedDateTime.now().toEpochSecond() >0){
            System.out.println("1");
            List<MapRecord<String, String, String>> list0 = opsForStream
            .read(Consumer.from(group, consumer)
            ,options
            ,StreamOffset.create(key, ReadOffset.lastConsumed()))
            ; // 할당
            
            log.info("{}",list0);
            System.out.println("2");
            List<MapRecord<String, String, String>> list1 = opsForStream
            .read(Consumer.from(group, consumer)
            ,options
            ,StreamOffset.create(key, ReadOffset.from("0"))
            ); // 읽기
            
            list1.forEach(t -> {
                
                log.info("{}",t);
            });
            System.out.println("3");
            log.info("{}",list1);

            // List<MapRecord<String, String, String>> list2 = opsForStream
            // .read(options,StreamOffset.create("s", ReadOffset.from("0")));

            // log.info("{}",list2);
            end.plus(10,ChronoUnit.SECONDS);
            System.out.println("blocked");
        }
		return ResponseEntity.ok(map);  
	}
    @PostMapping(path = "/send")
	public ResponseEntity<Map<String,Object>> send(ServletRequest request,@RequestBody Map<String,Object> map) {
		StreamOperations<String, String, String> opsForStream = redisTemplate.opsForStream();
        String key = String.valueOf(map.getOrDefault("key",new Date().getTime()));
        int loop = Integer.parseInt(String.valueOf(map.getOrDefault("loop", "1")));
        long interval = Long.parseLong(String.valueOf(map.getOrDefault("interval", 0)));
        Map<String,String> data = (Map<String, String>) map.get("data");
        while(loop -->0){
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            data.put("timeStemp", String.valueOf(new Date().getTime()));
            opsForStream.add(key, data);
        }
		return ResponseEntity.ok(map);  
	}

    @PostMapping(path = "/pubsub")
	public ResponseEntity<Map<String,Object>> pub(ServletRequest request,@RequestBody Map<String,Object> map) {
        redisMessageListenerContainer.addMessageListener((message, pattern) ->{
            log.info("message : {} pattern : {}",message,pattern);
        } , new ChannelTopic("my"));
        redisMessageListenerContainer.addMessageListener((message, pattern) ->{
            log.warn("message : {} pattern : {}",message,pattern);
        } , new ChannelTopic("my*"));
		return ResponseEntity.ok(map);  
	}
}

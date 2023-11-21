package com.example.demo.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public ConcurrentMapCacheManager cacheManager(){
        return new ConcurrentMapCacheManager();
    }
    @Bean("myKeyGen")
    public KeyGenerator myKeyGen(){
        return new Keygen();
    }
}

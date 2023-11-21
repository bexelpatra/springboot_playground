package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.entity.SpringTestEntity;

@Service
public interface MyService {
	List<SpringTestEntity> getEntity(SpringTestEntity entity);
}

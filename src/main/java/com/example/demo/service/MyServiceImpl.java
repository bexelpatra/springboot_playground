package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.QBlockCahinBackUpHist;
import com.example.demo.entity.SpringTestEntity;
import com.example.demo.repository.SpringTestRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyServiceImpl implements MyService{

	private final SpringTestRepository springTestRepository;
	@Override
//	@Cacheable(value = "testA")
	public List<SpringTestEntity> getEntity(SpringTestEntity entity) {
		// TODO Auto-generated method stub
		return springTestRepository.findAllByTestA(entity);
//		return null;
	}
}

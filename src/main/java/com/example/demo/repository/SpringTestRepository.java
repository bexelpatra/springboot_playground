package com.example.demo.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.SpringTestEntity;

@Repository
public interface SpringTestRepository extends JpaRepository<SpringTestEntity, String>{

	
	@Cacheable(value = "ttest", keyGenerator = "myKeyGen", unless = "#result.size() ==0")
	@Query(value = "/* 수정된거 */ select TEST_A,TEST_B ,TEST_C from SPRING_TEST where TEST_A = :#{#entity.key.testA} ",nativeQuery = true)
	List<SpringTestEntity> findAllByTestA(@Param("entity")SpringTestEntity entity);

}

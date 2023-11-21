package com.example.demo.entity;

import java.io.Serializable;

import com.example.demo.entity.compositeKey.SpringTestEntityKey;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Table(name="SPRING_TEST",schema="TEST_DB")
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SpringTestEntity implements Serializable{

	
	// @Id
	// @Basic
	// @Column(name="TEST_A")
	// private String testA;

	// @Basic
	// @Column(name="TEST_B")
	// private String testB;

	// @Basic
	// @Column(name="TEST_C")
	// private String testC;

	@EmbeddedId
	private SpringTestEntityKey key;
}

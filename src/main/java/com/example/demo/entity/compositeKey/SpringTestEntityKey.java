package com.example.demo.entity.compositeKey;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
@ToString
@AllArgsConstructor
@Builder
public class SpringTestEntityKey implements Serializable{
    @Column(name = "TEST_A")
    private String testA;
    @Column(name = "TEST_B")
    private String testB;
    @Column(name = "TEST_C")
    private String testC;
    

}

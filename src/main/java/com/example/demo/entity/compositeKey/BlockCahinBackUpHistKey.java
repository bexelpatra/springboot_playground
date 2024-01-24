package com.example.demo.entity.compositeKey;

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
public class BlockCahinBackUpHistKey {
    @Column(name = "BLOCK_NUMBER")
    private String blockNUmber;
    @Column(name = "TRNSC")
    private String trnsc;
}

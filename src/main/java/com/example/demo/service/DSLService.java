package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.BlockCahinBackUpHist;
import com.example.demo.entity.QBlockCahinBackUpHist;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DSLService {
    private final JPAQueryFactory jpaQueryFactory;

    public void test(String a){
        QBlockCahinBackUpHist q = QBlockCahinBackUpHist.blockCahinBackUpHist;
        Query query = jpaQueryFactory.selectFrom(q).createQuery();
        JPAQuery<BlockCahinBackUpHist> where = jpaQueryFactory.selectFrom(q).where(q.svcId.eq(a)).limit(20);
        List<BlockCahinBackUpHist> list = where.fetch();
        System.out.println("yes");
    }
}

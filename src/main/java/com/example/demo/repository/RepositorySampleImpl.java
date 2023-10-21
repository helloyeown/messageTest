package com.example.demo.repository;

import com.example.demo.model.dto.GetResponse;
import com.example.demo.model.entity.QEntitySample;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class RepositorySampleImpl implements RepositorySampleCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<GetResponse> getList(Pageable pageRequest) {
        return queryFactory.select(Projections.fields(GetResponse.class, QEntitySample.entitySample.name))
                .from(QEntitySample.entitySample)
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .orderBy(QEntitySample.entitySample.createdDt.desc())
                .fetch();
    }
}

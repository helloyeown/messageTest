package com.example.demo.repository.messages;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.demo.model.dto.messages.GetMessages;
import com.example.demo.model.entity.QMessage;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository     // DAO(Data Access Object) 역할을 하는 Repository 클래스임을 명시
@RequiredArgsConstructor
public class MessagesRepositoryCustomImpl implements MessagesRepositoryCustom {

    // QueryDSL을 이용하여 커스텀 쿼리 작성
    private final JPAQueryFactory queryFactory;

    @Override
    public List<GetMessages> getList(Pageable pageReqeust) {

        return queryFactory.select(Projections.bean(GetMessages.class, 
                    QMessage.message.id, 
                    QMessage.message.text, 
                    QMessage.message.sendedDt, 
                    QMessage.message.sender.name.as("sender"),
                    QMessage.message.receiver.name.as("receiver"),
                    QMessage.message.isRead))
        .from(QMessage.message)
        .leftJoin(QMessage.message.sender)
        .leftJoin(QMessage.message.receiver)
        .where(QMessage.message.delFlag.eq(false))
        .offset(pageReqeust.getOffset())
        .limit(pageReqeust.getPageSize())
        .orderBy(QMessage.message.sendedDt.desc())
        .fetch();
    }
}

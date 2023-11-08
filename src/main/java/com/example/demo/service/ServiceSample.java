package com.example.demo.service;

import com.example.demo.customException.DuplicateMemberException;
import com.example.demo.model.dto.*;
import com.example.demo.model.entity.EntitySample;
import com.example.demo.repository.RepositorySample;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceSample {

    private final RepositorySample repository;

    // 회원 생성
    @Transactional
    public CreateResponse create(CreateRequest request) {
        // 같은 이름의 사용자가 존재할 경우 예외 처리
        Optional<EntitySample> exist = repository.findByName(request.getName());

        if (exist.isPresent()) {
            throw new DuplicateMemberException(request.getName());  // request.getName은 로그에 남음
            // 사용자에게 보여지는 오류 메시지는 UserErrorCode에서 정의된 부분
        }

        EntitySample entity = new EntitySample();
        entity.setName(request.getName());
        return new CreateResponse(repository.save(entity).getId());
    }

    // id로 회원 검색
    public GetResponse getSample(Long id) {
        return new GetResponse(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 회원이 존재하지 않습니다.")).getName());
    }

    // 회원 목록
    public List<GetResponse> getPage(Pageable pageRequest) {
        return repository.getList(pageRequest);
    }

    // 이름 수정
    @Transactional
    public UpdateResponse update(Long id, UpdateRequest request) {
        EntitySample entity = repository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // 중복 이름 예외 처리
        Optional<EntitySample> exist = repository.findByName(request.getName());
        if (exist.isPresent()) {
            throw new DuplicateMemberException("이미 같은 이름의 사용자가 존재합니다.");
        }

        entity.setName(request.getName());
        return new UpdateResponse(entity);
    }

    // 회원 삭제 (물리적 삭제)
    @Transactional
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}

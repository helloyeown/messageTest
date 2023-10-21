package com.example.demo.service;

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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceSample {

    private final RepositorySample repository;

    @Transactional
    public CreateResponse create(CreateRequest request) {
        EntitySample entity = new EntitySample();
        entity.setName(request.getName());
        return new CreateResponse(repository.save(entity).getId());
    }

    public GetResponse getSample(Long id) {
        return new GetResponse(repository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND)).getName());
    }

    public List<GetResponse> getPage(Pageable pageRequest) {
        return repository.getList(pageRequest);
    }

    @Transactional
    public UpdateResponse update(Long id, UpdateRequest request) {
        EntitySample entity = repository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        entity.setName(request.getName());
        return new UpdateResponse(entity);
    }
}

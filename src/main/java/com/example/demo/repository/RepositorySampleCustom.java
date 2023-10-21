package com.example.demo.repository;

import com.example.demo.model.dto.GetResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RepositorySampleCustom {

    List<GetResponse> getList(Pageable pageRequest);
}

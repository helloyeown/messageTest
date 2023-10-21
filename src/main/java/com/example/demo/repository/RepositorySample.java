package com.example.demo.repository;

import com.example.demo.model.entity.EntitySample;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorySample extends JpaRepository<EntitySample, Long>, RepositorySampleCustom {
}

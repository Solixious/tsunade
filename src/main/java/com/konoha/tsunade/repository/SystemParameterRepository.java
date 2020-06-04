package com.konoha.tsunade.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.konoha.tsunade.model.entity.SystemParameterEntity;

@Repository
public interface SystemParameterRepository extends TsunadePagingAndSortingRepository<SystemParameterEntity, Long> {

	Optional<SystemParameterEntity> findByVariableAndActiveTrue(String variable);
}

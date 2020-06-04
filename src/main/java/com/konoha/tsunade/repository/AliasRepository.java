package com.konoha.tsunade.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.konoha.tsunade.model.entity.AliasEntity;

@Repository
public interface AliasRepository extends TsunadePagingAndSortingRepository<AliasEntity, Long> {

	Optional<AliasEntity> findByUsernameAndActiveTrue(String username);
}

package com.konoha.tsunade.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.konoha.tsunade.model.entity.RoomEntity;

@Repository
public interface RoomRepository extends TsunadePagingAndSortingRepository<RoomEntity, Long> {

	Long countByPathAndActiveTrue(String path);

	Optional<RoomEntity> findByPathAndActiveTrue(String path);
}

package com.konoha.tsunade.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.konoha.tsunade.model.entity.PostEntity;
import com.konoha.tsunade.model.entity.RoomEntity;

@Repository
public interface PostRepository extends TsunadePagingAndSortingRepository<PostEntity, Long> {

	Page<PostEntity> findByRoomAndActiveTrue(RoomEntity roomEntity, Pageable pageable);
}

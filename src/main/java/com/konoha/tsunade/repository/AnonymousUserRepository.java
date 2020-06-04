package com.konoha.tsunade.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.konoha.tsunade.model.entity.AliasEntity;
import com.konoha.tsunade.model.entity.AnonymousUserEntity;
import com.konoha.tsunade.model.entity.RoomEntity;
import com.konoha.tsunade.model.entity.UserEntity;

@Repository
public interface AnonymousUserRepository extends TsunadePagingAndSortingRepository<AnonymousUserEntity, Long> {

	Optional<AnonymousUserEntity> findByRoomAndAliasAndActiveTrue(RoomEntity room, AliasEntity alias);

	Optional<AnonymousUserEntity> findByRoomAndUserAndActiveTrue(RoomEntity room, UserEntity user);

	List<AnonymousUserEntity> findByRoomAndActiveTrue(RoomEntity roomEntity);

	@Query("SELECT u FROM AnonymousUserEntity u WHERE u.user = ?1 AND u.alias = u.room.author AND u.active=true")
	Page<AnonymousUserEntity> findByUser(UserEntity user, Pageable pageable);
}

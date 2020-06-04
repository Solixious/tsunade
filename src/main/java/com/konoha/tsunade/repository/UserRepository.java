package com.konoha.tsunade.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.konoha.tsunade.model.entity.UserEntity;

@Repository
public interface UserRepository extends TsunadePagingAndSortingRepository<UserEntity, Long> {

	Optional<UserEntity> findByUserNameAndActiveTrue(String userName);

	Optional<UserEntity> findByEmailAndActiveTrue(String email);

	Long countByUserNameAndActiveTrue(String userName);
}

package com.konoha.tsunade.repository;

import org.springframework.stereotype.Repository;

import com.konoha.tsunade.model.entity.RoleEntity;

@Repository
public interface RoleRepository extends TsunadePagingAndSortingRepository<RoleEntity, Long> {

	RoleEntity findByRoleNameAndActiveTrue(String roleName);
}

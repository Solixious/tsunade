package com.konoha.tsunade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.konoha.tsunade.model.entity.RoleEntity;
import com.konoha.tsunade.repository.RoleRepository;
import com.konoha.tsunade.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public String getRole(final String name) {
		if (!StringUtils.isEmpty(name)) {
			final RoleEntity roleEntity = roleRepository.findByRoleNameAndActiveTrue(name);
			if (roleEntity != null && !StringUtils.isEmpty(roleEntity.getRoleName())) {
				return roleEntity.getRoleName();
			}
		}
		return null;
	}

	@Override
	public void addRole(final String name) {
		if (!StringUtils.isEmpty(name)) {
			roleRepository.save(RoleEntity.builder().roleName(name).build());
		}
	}

}
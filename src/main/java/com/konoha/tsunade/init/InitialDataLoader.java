package com.konoha.tsunade.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.konoha.tsunade.constants.Role;
import com.konoha.tsunade.constants.SystemParameter;
import com.konoha.tsunade.model.request.UpdateSystemParameterRequest;
import com.konoha.tsunade.service.RoleService;
import com.konoha.tsunade.service.SystemParameterService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private RoleService roleService;

	@Autowired
	private SystemParameterService systemParameterService;

	@Override
	@Transactional
	public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
		for (Role role : Role.values()) {
			createRoleIfNotFound(role.name());
		}
		for (SystemParameter systemParameter : SystemParameter.values()) {
			addSystemParameter(systemParameter);
		}
	}

	@Transactional
	private void addSystemParameter(final SystemParameter systemParameter) {
		try {
			final String value = systemParameterService.getSystemParameterString(systemParameter);
			if (value == null) {
				systemParameterService.addSystemParameter(UpdateSystemParameterRequest.builder()
						.variable(systemParameter.name()).value(systemParameter.getDefaultValue()).build());
			}
		} catch (Exception e) {
			log.error("Error while adding system parameter with name: {} and default value: {}", systemParameter.name(),
					systemParameter.getDefaultValue(), e);
		}
	}

	@Transactional
	private String createRoleIfNotFound(final String name) {
		final String roleName = roleService.getRole(name);
		if (roleName == null) {
			roleService.addRole(name);
		}
		return roleName;
	}
}

package com.konoha.tsunade.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.konoha.tsunade.constants.CacheName;
import com.konoha.tsunade.constants.ErrorCode;
import com.konoha.tsunade.constants.SystemParameter;
import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.model.entity.SystemParameterEntity;
import com.konoha.tsunade.model.request.UpdateSystemParameterRequest;
import com.konoha.tsunade.model.response.SystemParameterResponse;
import com.konoha.tsunade.repository.SystemParameterRepository;
import com.konoha.tsunade.service.SystemParameterService;

@Service
public class SystemParameterServiceImpl implements SystemParameterService {

	@Autowired
	private SystemParameterRepository systemParameterRepository;

	@Override
	public SystemParameterResponse addSystemParameter(final UpdateSystemParameterRequest request)
			throws BaseBusinessException {
		if (request == null || StringUtils.isBlank(request.getVariable()) || StringUtils.isBlank(request.getValue())) {
			throw new BaseBusinessException(ErrorCode.INSUFFICIENT_PARAMETERS);
		}
		SystemParameterEntity systemParameterEntity = SystemParameterEntity.builder().variable(request.getVariable())
				.value(request.getValue()).build();
		systemParameterEntity = systemParameterRepository.save(systemParameterEntity);
		return convertToSystemParameterResponse(systemParameterEntity);
	}

	@Override
	public SystemParameterResponse updateSystemParameter(final UpdateSystemParameterRequest request)
			throws BaseBusinessException {
		if (request == null || StringUtils.isBlank(request.getVariable()) || StringUtils.isBlank(request.getValue())) {
			throw new BaseBusinessException(ErrorCode.INSUFFICIENT_PARAMETERS);
		}
		SystemParameterEntity systemParameterEntity = systemParameterRepository
				.findByVariableAndActiveTrue(request.getVariable())
				.orElseThrow(() -> new BaseBusinessException(ErrorCode.SYSTEM_PARAMETER_NOT_FOUND));
		systemParameterEntity.setValue(request.getValue());
		systemParameterEntity = systemParameterRepository.save(systemParameterEntity);
		return convertToSystemParameterResponse(systemParameterEntity);
	}

	@Override
	@Cacheable(value = CacheName.SYSTEM_PARAMETER, key = CacheName.SYSTEM_PARAMETER_NAME)
	public SystemParameterResponse getSystemParameter(final SystemParameter systemParameter)
			throws BaseBusinessException {
		final Optional<SystemParameterEntity> systemParameterEntity = systemParameterRepository
				.findByVariableAndActiveTrue(systemParameter.name());
		if (systemParameterEntity.isEmpty()) {
			return null;
		}
		return convertToSystemParameterResponse(systemParameterEntity.get());
	}

	@Override
	public Integer getSystemParameterInt(final SystemParameter systemParameter) throws BaseBusinessException {
		return Integer.valueOf(getSystemParameter(systemParameter).getValue());
	}

	@Override
	public String getSystemParameterString(final SystemParameter systemParameter) throws BaseBusinessException {
		SystemParameterResponse response = getSystemParameter(systemParameter);
		return response != null ? response.getValue() : null;
	}

	private SystemParameterResponse convertToSystemParameterResponse(
			final SystemParameterEntity systemParameterEntity) {
		return SystemParameterResponse.builder().variable(systemParameterEntity.getVariable())
				.value(systemParameterEntity.getValue()).build();
	}
}

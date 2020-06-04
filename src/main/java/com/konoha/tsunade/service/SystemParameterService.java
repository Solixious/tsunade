package com.konoha.tsunade.service;

import com.konoha.tsunade.constants.SystemParameter;
import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.model.request.UpdateSystemParameterRequest;
import com.konoha.tsunade.model.response.SystemParameterResponse;

public interface SystemParameterService {

	/**
	 * @param request Request object to update system parameter
	 * @return Updated system parameter response
	 * @throws BaseBusinessException
	 */
	SystemParameterResponse updateSystemParameter(UpdateSystemParameterRequest request) throws BaseBusinessException;

	/**
	 * @param request Request object to add system parameter
	 * @return System parameter response
	 * @throws BaseBusinessException
	 */
	SystemParameterResponse addSystemParameter(UpdateSystemParameterRequest request) throws BaseBusinessException;

	/**
	 * @param systemParameter system parameter
	 * @return System parameter response
	 * @throws BaseBusinessException
	 */
	SystemParameterResponse getSystemParameter(SystemParameter systemParameter) throws BaseBusinessException;

	/**
	 * @param systemParameter system parameter
	 * @return System parameter integer value
	 * @throws BaseBusinessException
	 */
	Integer getSystemParameterInt(SystemParameter systemParameter) throws BaseBusinessException;

	/**
	 * @param systemParameter system parameter
	 * @return System parameter string value
	 * @throws BaseBusinessException
	 */
	String getSystemParameterString(SystemParameter systemParameter) throws BaseBusinessException;
}

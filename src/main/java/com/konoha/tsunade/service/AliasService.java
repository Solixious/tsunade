package com.konoha.tsunade.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.model.request.AddAliasRequest;
import com.konoha.tsunade.model.response.AliasResponse;

public interface AliasService {

	/**
	 * @param request Request for adding new alias
	 * @param file    profile picture to be updated
	 * @return Response after adding new alias
	 * @throws BaseBusinessException exception
	 */
	AliasResponse addAlias(AddAliasRequest request, MultipartFile file) throws BaseBusinessException;

	/**
	 * @param pageNo Page Number
	 * @return List of all aliases in the system
	 * @throws BaseBusinessException if no aliases exist
	 */
	Page<AliasResponse> getAllAlias(Integer pageNo) throws BaseBusinessException;

	/**
	 * @param userName User name of alias to update
	 * @param request  Update request object
	 * @param file     Profile picture to be updated
	 * @return Updated alias
	 */
	AliasResponse updateAlias(String userName, AddAliasRequest request, MultipartFile file)
			throws BaseBusinessException;
}

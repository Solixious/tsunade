package com.konoha.tsunade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.konoha.tsunade.constants.ErrorCode;
import com.konoha.tsunade.constants.UrlMapping;
import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.model.request.AddAliasRequest;
import com.konoha.tsunade.model.response.ErrorResponse;
import com.konoha.tsunade.service.AliasService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = UrlMapping.ADMIN + UrlMapping.ALIAS)
@Slf4j
public class AdminAliasController {

	@Autowired
	private AliasService aliasService;

	@PostMapping
	public Object addAlias(@ModelAttribute final AddAliasRequest request,
			@RequestParam("file") final MultipartFile file) {
		try {
			return aliasService.addAlias(request, file);
		} catch (BaseBusinessException e) {
			log.error("Error while adding new alias, request: {}, errorCode: {}, errorDescription: {}", request,
					e.getErrorCode(), e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while adding new alias, request: {}, errorCode: {}, errorDescription: {}", request,
					ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}

	@PostMapping(value = UrlMapping.USER_NAME)
	public Object updateAlias(@ModelAttribute final AddAliasRequest request,
			@RequestParam(value = "file", required = false) final MultipartFile file,
			@PathVariable final String userName) {
		try {
			return aliasService.updateAlias(userName, request, file);
		} catch (BaseBusinessException e) {
			log.error("Error while updating alias, request: {}, errorCode: {}, errorDescription: {}", request,
					e.getErrorCode(), e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while updating alias, request: {}, errorCode: {}, errorDescription: {}", request,
					ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}

	@GetMapping
	public Object getAlias() {
		try {
			return aliasService.getAllAlias(0);
		} catch (BaseBusinessException e) {
			log.error("Error while fetching all alias, errorCode: {}, errorDescription: {}", e.getErrorCode(),
					e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while fetching all alias, errorCode: {}, errorDescription: {}",
					ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}

	@GetMapping(value = UrlMapping.PAGE)
	public Object getAliasWithPageNo(@PathVariable final Integer pageNo) {
		try {
			return aliasService.getAllAlias(pageNo);
		} catch (BaseBusinessException e) {
			log.error("Error while fetching all alias, errorCode: {}, errorDescription: {}", e.getErrorCode(),
					e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while fetching all alias, errorCode: {}, errorDescription: {}",
					ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}
}
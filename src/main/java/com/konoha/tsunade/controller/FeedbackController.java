package com.konoha.tsunade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.konoha.tsunade.constants.ErrorCode;
import com.konoha.tsunade.constants.UrlMapping;
import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.model.response.ErrorResponse;
import com.konoha.tsunade.service.FeedbackService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = UrlMapping.ADMIN + UrlMapping.FEEDBACK)
@Slf4j
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	@GetMapping
	public Object get() {
		try {
			return feedbackService.getAllFeedback(0);
		} catch (BaseBusinessException e) {
			log.error("Error while getting all alias, errorCode: {}, errorDescription: {}", e.getErrorCode(),
					e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while getting all alias, errorCode: {}, errorDescription: {}",
					ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}

	@GetMapping(value = UrlMapping.PAGE)
	public Object get(@PathVariable final Integer pageNo) {
		try {
			return feedbackService.getAllFeedback(pageNo);
		} catch (BaseBusinessException e) {
			log.error("Error while getting all alias, errorCode: {}, errorDescription: {}", e.getErrorCode(),
					e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while getting all alias, errorCode: {}, errorDescription: {}",
					ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}
}

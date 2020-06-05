package com.konoha.tsunade.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.konoha.tsunade.constants.ErrorCode;
import com.konoha.tsunade.constants.UrlMapping;
import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.model.request.AddFeedbackRequest;
import com.konoha.tsunade.model.response.ErrorResponse;
import com.konoha.tsunade.service.FeedbackService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = UrlMapping.FEEDBACK)
@Slf4j
public class AdminFeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	@PostMapping
	public Object addFeedback(@RequestBody final AddFeedbackRequest request, final Principal principal) {
		try {
			return feedbackService.addFeedback(request, principal != null ? principal.getName() : null);
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
}

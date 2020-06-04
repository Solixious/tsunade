package com.konoha.tsunade.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.konoha.tsunade.constants.ErrorCode;
import com.konoha.tsunade.constants.UrlMapping;
import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.model.request.AddReactionRequest;
import com.konoha.tsunade.model.request.RemoveReactionRequest;
import com.konoha.tsunade.model.response.ErrorResponse;
import com.konoha.tsunade.service.ReactionService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = UrlMapping.REACTION)
@Slf4j
public class ReactionController {

	@Autowired
	private ReactionService reactionService;

	@PostMapping
	public Object addReaction(@RequestBody final AddReactionRequest request, final Principal principal) {
		try {
			return reactionService.addReaction(request, principal.getName());
		} catch (BaseBusinessException e) {
			log.error("Error while adding reaction, request: {}, errorCode: {}, errorDescription: {}", request,
					e.getErrorCode(), e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while adding reaction, request: {}, errorCode: {}, errorDescription: {}", request,
					ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}

	@DeleteMapping
	public Object removeReaction(@RequestBody final RemoveReactionRequest request, final Principal principal) {
		try {
			return reactionService.removeReaction(request, principal.getName());
		} catch (BaseBusinessException e) {
			log.error("Error while removing reaction, request: {}, errorCode: {}, errorDescription: {}", request,
					e.getErrorCode(), e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while removing reaction, request: {}, errorCode: {}, errorDescription: {}", request,
					ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}
}

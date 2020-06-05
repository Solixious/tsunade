package com.konoha.tsunade.service;

import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.model.request.AddFeedbackRequest;
import com.konoha.tsunade.model.response.FeedbackResponse;

public interface FeedbackService {

	/**
	 * @param request Add feedback request
	 * @param username User adding the feedback
	 * @return Feedback response object
	 * @throws BaseBusinessException
	 */
	FeedbackResponse addFeedback(AddFeedbackRequest request, String username) throws BaseBusinessException;
}

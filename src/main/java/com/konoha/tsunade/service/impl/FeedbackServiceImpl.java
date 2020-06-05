package com.konoha.tsunade.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.konoha.tsunade.constants.FeedbackStatus;
import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.model.entity.FeedbackEntity;
import com.konoha.tsunade.model.entity.UserEntity;
import com.konoha.tsunade.model.request.AddFeedbackRequest;
import com.konoha.tsunade.model.response.FeedbackResponse;
import com.konoha.tsunade.repository.FeedbackRepository;
import com.konoha.tsunade.repository.UserRepository;
import com.konoha.tsunade.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public FeedbackResponse addFeedback(final AddFeedbackRequest request, final String username)
			throws BaseBusinessException {
		UserEntity user = null;
		if (StringUtils.isNotBlank(username)) {
			user = userRepository.findByUserNameAndActiveTrue(username).orElse(null);
		}
		FeedbackEntity feedback = FeedbackEntity.builder().feedback(request.getFeedback()).user(user)
				.status(FeedbackStatus.UNREAD).build();
		feedback = feedbackRepository.save(feedback);
		return convertToFeedbackResponse(feedback);
	}

	private FeedbackResponse convertToFeedbackResponse(FeedbackEntity feedback) {
		return FeedbackResponse.builder().id(feedback.getId()).feedback(feedback.getFeedback())
				.status(feedback.getStatus()).build();
	}

}

package com.konoha.tsunade.model.response;


import com.konoha.tsunade.constants.FeedbackStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {

	private Long id;
	private String feedback;
	private String created;
	private FeedbackStatus status;
}

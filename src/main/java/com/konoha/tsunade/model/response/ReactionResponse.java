package com.konoha.tsunade.model.response;

import com.konoha.tsunade.constants.ReactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReactionResponse {

	private String postId;
	private String path;
	private ReactionType reactionType;
}

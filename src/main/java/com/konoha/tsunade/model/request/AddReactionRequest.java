package com.konoha.tsunade.model.request;

import com.konoha.tsunade.constants.ReactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddReactionRequest {

	private String postId;
	private String path;
	private ReactionType reactionType;
}

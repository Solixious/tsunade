package com.konoha.tsunade.model.response;

import java.io.Serializable;

import com.konoha.tsunade.constants.ReactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse implements Serializable {

	private static final long serialVersionUID = 3137200437575811284L;
	private Long postId;
	private String content;
	private String path;
	private String created;
	private AliasResponse author;
	private Long agree;
	private Long disagree;
	private ReactionType userReaction;
}

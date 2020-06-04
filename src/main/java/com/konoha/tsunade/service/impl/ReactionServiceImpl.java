package com.konoha.tsunade.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.konoha.tsunade.constants.ErrorCode;
import com.konoha.tsunade.constants.ReactionType;
import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.model.entity.AnonymousUserEntity;
import com.konoha.tsunade.model.entity.PostEntity;
import com.konoha.tsunade.model.entity.ReactionEntity;
import com.konoha.tsunade.model.entity.UserEntity;
import com.konoha.tsunade.model.request.AddReactionRequest;
import com.konoha.tsunade.model.request.RemoveReactionRequest;
import com.konoha.tsunade.model.response.ReactionResponse;
import com.konoha.tsunade.repository.AnonymousUserRepository;
import com.konoha.tsunade.repository.PostRepository;
import com.konoha.tsunade.repository.ReactionRepository;
import com.konoha.tsunade.repository.UserRepository;
import com.konoha.tsunade.service.AnonymousUserService;
import com.konoha.tsunade.service.ReactionService;

@Service
public class ReactionServiceImpl implements ReactionService {

	@Autowired
	private ReactionRepository reactionRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private AnonymousUserRepository anonymousUserRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AnonymousUserService anonymousUserService;

	@Override
	public ReactionResponse addReaction(final AddReactionRequest request, final String username)
			throws BaseBusinessException {
		if (request == null || StringUtils.isBlank(request.getPath()) || StringUtils.isBlank(request.getPostId())
				|| request.getReactionType() == null) {
			throw new BaseBusinessException(ErrorCode.INSUFFICIENT_PARAMETERS);
		}
		final PostEntity post = postRepository.findOne(Long.parseLong(request.getPostId()));
		if (!request.getPath().equals(post.getRoom().getPath())) {
			throw new BaseBusinessException(ErrorCode.POST_AND_ROOM_MISMATCH);
		}
		final UserEntity user = userRepository.findByUserNameAndActiveTrue(username)
				.orElseThrow(() -> new BaseBusinessException(ErrorCode.USER_NOT_FOUND));
		final AnonymousUserEntity anonymousUser = anonymousUserRepository
				.findByRoomAndUserAndActiveTrue(post.getRoom(), user)
				.orElse(anonymousUserService.getAnonymousUser(post.getRoom(), user, true));
		ReactionEntity reactionEntity = reactionRepository.findByPostAndAnonymousUser(post, anonymousUser)
				.orElse(ReactionEntity.builder().post(post).anonymousUser(anonymousUser).build());
		reactionEntity.setReactionType(request.getReactionType());
		reactionEntity = reactionRepository.save(reactionEntity);
		return convertToReactionResponse(reactionEntity);
	}

	@Override
	public Boolean removeReaction(final RemoveReactionRequest request, final String username)
			throws BaseBusinessException {
		if (request == null || StringUtils.isBlank(request.getPath()) || StringUtils.isBlank(request.getPostId())) {
			throw new BaseBusinessException(ErrorCode.INSUFFICIENT_PARAMETERS);
		}
		final PostEntity post = postRepository.findOne(Long.parseLong(request.getPostId()));
		if (!request.getPath().equals(post.getRoom().getPath())) {
			throw new BaseBusinessException(ErrorCode.POST_AND_ROOM_MISMATCH);
		}
		final UserEntity user = userRepository.findByUserNameAndActiveTrue(username)
				.orElseThrow(() -> new BaseBusinessException(ErrorCode.USER_NOT_FOUND));
		final AnonymousUserEntity anonymousUser = anonymousUserRepository
				.findByRoomAndUserAndActiveTrue(post.getRoom(), user)
				.orElseThrow(() -> new BaseBusinessException(ErrorCode.ANONYMOUS_USER_NOT_FOUND));
		ReactionEntity reactionEntity = reactionRepository.findByPostAndAnonymousUser(post, anonymousUser)
				.orElseThrow(() -> new BaseBusinessException(ErrorCode.REACTION_NOT_FOUND));
		reactionEntity.setReactionType(ReactionType.NONE);
		reactionEntity = reactionRepository.save(reactionEntity);
		return true;
	}

	private ReactionResponse convertToReactionResponse(final ReactionEntity reactionEntity) {
		return ReactionResponse.builder().path(reactionEntity.getPost().getRoom().getPath())
				.postId(reactionEntity.getPost().getId().toString()).reactionType(reactionEntity.getReactionType())
				.build();
	}
}

package com.konoha.tsunade.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.konoha.tsunade.constants.ErrorCode;
import com.konoha.tsunade.constants.ReactionType;
import com.konoha.tsunade.constants.SystemParameter;
import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.model.entity.AnonymousUserEntity;
import com.konoha.tsunade.model.entity.PostEntity;
import com.konoha.tsunade.model.entity.ReactionEntity;
import com.konoha.tsunade.model.entity.RoomEntity;
import com.konoha.tsunade.model.entity.UserEntity;
import com.konoha.tsunade.model.request.CreatePostRequest;
import com.konoha.tsunade.model.response.AliasResponse;
import com.konoha.tsunade.model.response.PostResponse;
import com.konoha.tsunade.repository.PostRepository;
import com.konoha.tsunade.repository.ReactionRepository;
import com.konoha.tsunade.repository.RoomRepository;
import com.konoha.tsunade.repository.UserRepository;
import com.konoha.tsunade.service.AnonymousUserService;
import com.konoha.tsunade.service.PostService;
import com.konoha.tsunade.service.SystemParameterService;
import com.konoha.tsunade.service.TimeService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ReactionRepository reactionRepository;

	@Autowired
	private AnonymousUserService anonymousUserService;

	@Autowired
	private SystemParameterService systemParameterService;

	@Autowired
	private TimeService timeService;

	@Override
	public PostResponse createPost(final CreatePostRequest request, final String username)
			throws BaseBusinessException {
		if (StringUtils.isBlank(username)) {
			throw new BaseBusinessException(ErrorCode.NULL_USER);
		}
		if (StringUtils.isBlank(request.getPath()) || StringUtils.isBlank(request.getContent())) {
			throw new BaseBusinessException(ErrorCode.INSUFFICIENT_PARAMETERS);
		}
		final RoomEntity roomEntity = roomRepository.findByPathAndActiveTrue(request.getPath())
				.orElseThrow(() -> new BaseBusinessException(ErrorCode.ROOM_NOT_FOUND));
		final UserEntity userEntity = userRepository.findByUserNameAndActiveTrue(username)
				.orElseThrow(() -> new BaseBusinessException(ErrorCode.USER_NOT_FOUND));
		final PostEntity postEntity = PostEntity.builder().room(roomEntity)
				.author(anonymousUserService.getAnonymousUser(roomEntity, userEntity, true).getAlias())
				.content(request.getContent()).build();
		postRepository.save(postEntity);
		return convertToPostResponse(postEntity);
	}

	@Override
	public Page<PostResponse> getPostsInRoom(final String path, final Integer pageNo, final String username)
			throws BaseBusinessException {
		final RoomEntity roomEntity = roomRepository.findByPathAndActiveTrue(path)
				.orElseThrow(() -> new BaseBusinessException(ErrorCode.ROOM_NOT_FOUND));
		final Page<PostEntity> posts = postRepository.findByRoomAndActiveTrue(roomEntity,
				PageRequest.of(pageNo, systemParameterService.getSystemParameterInt(SystemParameter.PAGE_SIZE_POSTS)));
		if (posts == null || posts.isEmpty()) {
			throw new BaseBusinessException(ErrorCode.POST_NOT_FOUND);
		}
		if (StringUtils.isNotBlank(username)) {
			final UserEntity user = userRepository.findByUserNameAndActiveTrue(username)
					.orElseThrow(() -> new BaseBusinessException(ErrorCode.USER_NOT_FOUND));
			final AnonymousUserEntity anonymousUser = anonymousUserService.getAnonymousUser(roomEntity, user, false);
			return posts.map(e -> convertToPostResponse(e, anonymousUser));
		}
		return posts.map(this::convertToPostResponse);
	}

	private PostResponse convertToPostResponse(final PostEntity postEntity) {
		return PostResponse.builder().postId(postEntity.getId()).content(postEntity.getContent())
				.path(postEntity.getRoom().getPath())
				.author(AliasResponse.builder().username(postEntity.getAuthor().getUsername())
						.profilePicture(postEntity.getAuthor().getProfilePicture()).build())
				.created(timeService.toRelative(postEntity.getCreateDate()))
				.agree(reactionRepository.countByPostAndReactionType(postEntity, ReactionType.AGREE))
				.disagree(reactionRepository.countByPostAndReactionType(postEntity, ReactionType.DISAGREE)).build();
	}

	private PostResponse convertToPostResponse(final PostEntity postEntity,
			final AnonymousUserEntity anonymousUserEntity) {
		return PostResponse.builder().postId(postEntity.getId()).content(postEntity.getContent())
				.path(postEntity.getRoom().getPath())
				.author(AliasResponse.builder().username(postEntity.getAuthor().getUsername())
						.profilePicture(postEntity.getAuthor().getProfilePicture()).build())
				.created(timeService.toRelative(postEntity.getCreateDate()))
				.agree(reactionRepository.countByPostAndReactionType(postEntity, ReactionType.AGREE))
				.disagree(reactionRepository.countByPostAndReactionType(postEntity, ReactionType.DISAGREE))
				.userReaction(reactionRepository.findByPostAndAnonymousUser(postEntity, anonymousUserEntity)
						.orElse(ReactionEntity.builder().build()).getReactionType())
				.build();
	}
}

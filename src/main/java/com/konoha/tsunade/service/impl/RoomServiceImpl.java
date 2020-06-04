package com.konoha.tsunade.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.konoha.tsunade.constants.Constants;
import com.konoha.tsunade.constants.ErrorCode;
import com.konoha.tsunade.constants.SystemParameter;
import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.model.entity.AnonymousUserEntity;
import com.konoha.tsunade.model.entity.RoomEntity;
import com.konoha.tsunade.model.entity.UserEntity;
import com.konoha.tsunade.model.request.CreateRoomRequest;
import com.konoha.tsunade.model.request.UpdateRoomRequest;
import com.konoha.tsunade.model.response.AliasResponse;
import com.konoha.tsunade.model.response.PostResponse;
import com.konoha.tsunade.model.response.RoomDetailResponse;
import com.konoha.tsunade.model.response.RoomResponse;
import com.konoha.tsunade.repository.AnonymousUserRepository;
import com.konoha.tsunade.repository.RoomRepository;
import com.konoha.tsunade.repository.UserRepository;
import com.konoha.tsunade.service.AnonymousUserService;
import com.konoha.tsunade.service.PostService;
import com.konoha.tsunade.service.RoomService;
import com.konoha.tsunade.service.SystemParameterService;
import com.konoha.tsunade.service.TimeService;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AnonymousUserService anonymousUserService;

	@Autowired
	private AnonymousUserRepository anonymousUserRepository;

	@Autowired
	private SystemParameterService systemParameterService;

	@Autowired
	private PostService postService;

	@Autowired
	private TimeService timeService;

	@Override
	public RoomResponse createRoom(final CreateRoomRequest request, final String username)
			throws BaseBusinessException {
		if (StringUtils.isBlank(username)) {
			throw new BaseBusinessException(ErrorCode.NULL_USER);
		}
		if (StringUtils.isBlank(request.getTitle()) || StringUtils.isBlank(request.getDescription())) {
			throw new BaseBusinessException(ErrorCode.INSUFFICIENT_PARAMETERS);
		}
		final UserEntity userEntity = userRepository.findByUserNameAndActiveTrue(username)
				.orElseThrow(() -> new BaseBusinessException(ErrorCode.USER_NOT_FOUND));
		RoomEntity roomEntity = roomRepository.save(RoomEntity.builder().title(request.getTitle())
				.description(request.getDescription()).path(generatePath(request.getTitle())).closed(false).build());

		roomEntity.setAuthor(anonymousUserService.getAnonymousUser(roomEntity, userEntity, true).getAlias());

		roomEntity = roomRepository.save(roomEntity);

		return convertToRoomResponse(roomEntity);
	}

	@Override
	public RoomResponse updateRoom(final UpdateRoomRequest request, final String username)
			throws BaseBusinessException {
		if (StringUtils.isBlank(request.getPath())) {
			throw new BaseBusinessException(ErrorCode.INSUFFICIENT_PARAMETERS);
		}
		if (StringUtils.isBlank(username)) {
			throw new BaseBusinessException(ErrorCode.NULL_USER);
		}

		final UserEntity userEntity = userRepository.findByUserNameAndActiveTrue(username)
				.orElseThrow(() -> new BaseBusinessException(ErrorCode.USER_NOT_FOUND));
		RoomEntity roomEntity = roomRepository.findByPathAndActiveTrue(request.getPath())
				.orElseThrow(() -> new BaseBusinessException(ErrorCode.ROOM_NOT_FOUND));

		final AnonymousUserEntity anonymousUserEntity = anonymousUserService.getAnonymousUser(roomEntity, userEntity,
				false);
		if (anonymousUserEntity == null || !anonymousUserEntity.getAlias().equals(roomEntity.getAuthor())) {
			throw new BaseBusinessException(ErrorCode.UNAUTHORIZED);
		}

		if (StringUtils.isNotBlank(request.getTitle()) && !roomEntity.getTitle().equals(request.getTitle())) {
			roomEntity.setTitle(request.getTitle());
			roomEntity.setPath(generatePath(request.getTitle()));
		}
		if (StringUtils.isNotBlank(request.getDescription())
				&& !roomEntity.getDescription().equals(request.getDescription())) {
			roomEntity.setDescription(request.getDescription());
		}
		if (request.getClosed() != null && roomEntity.getClosed() != request.getClosed()) {
			roomEntity.setClosed(request.getClosed());
		}

		roomEntity = roomRepository.save(roomEntity);
		return convertToRoomResponse(roomEntity);
	}

	@Override
	public RoomDetailResponse getRoomDetail(final String path, final Integer pageNo, final String username)
			throws BaseBusinessException {
		final RoomEntity roomEntity = roomRepository.findByPathAndActiveTrue(path)
				.orElseThrow(() -> new BaseBusinessException(ErrorCode.ROOM_NOT_FOUND));
		final Page<PostResponse> posts = postService.getPostsInRoom(path, pageNo, username);
		posts.forEach(e -> e.setPath(null));
		System.out.println("Retrieving room " + path + "form database.");
		return convertToRoomDetailResponse(roomEntity, posts);
	}

	@Override
	public Page<RoomResponse> getRecentRoom(final Integer pageNo) throws BaseBusinessException {
		Page<RoomEntity> roomEntities = roomRepository.findAll(PageRequest.of(pageNo,
				systemParameterService.getSystemParameterInt(SystemParameter.PAGE_SIZE_RECENT_ROOMS)));
		if (roomEntities == null || roomEntities.isEmpty()) {
			throw new BaseBusinessException(ErrorCode.ROOM_NOT_FOUND);
		}
		return roomEntities.map(this::convertToRoomResponse);
	}

	@Override
	public Page<RoomResponse> getRoomsByUser(final String username, final Integer pageNo) throws BaseBusinessException {
		final UserEntity userEntity = userRepository.findByUserNameAndActiveTrue(username)
				.orElseThrow(() -> new BaseBusinessException(ErrorCode.USER_NOT_FOUND));
		final Page<AnonymousUserEntity> anonymousUserEntities = anonymousUserRepository.findByUser(userEntity,
				PageRequest.of(pageNo,
						systemParameterService.getSystemParameterInt(SystemParameter.PAGE_SIZE_USER_ROOMS)));
		if (anonymousUserEntities == null || anonymousUserEntities.isEmpty()) {
			throw new BaseBusinessException(ErrorCode.ROOM_NOT_FOUND);
		}
		return anonymousUserEntities.map(this::convertToRoomResponse);
	}

	private RoomResponse convertToRoomResponse(final RoomEntity roomEntity) {
		return RoomResponse.builder().title(roomEntity.getTitle()).description(roomEntity.getDescription())
				.path(roomEntity.getPath())
				.author(AliasResponse.builder().username(roomEntity.getAuthor().getUsername())
						.profilePicture(roomEntity.getAuthor().getProfilePicture()).build())
				.created(timeService.toRelative(roomEntity.getCreateDate())).build();
	}

	private RoomResponse convertToRoomResponse(final AnonymousUserEntity anonymousUserEntity) {
		return convertToRoomResponse(anonymousUserEntity.getRoom());
	}

	private RoomDetailResponse convertToRoomDetailResponse(final RoomEntity roomEntity,
			final Page<PostResponse> posts) {
		return RoomDetailResponse.builder().title(roomEntity.getTitle()).description(roomEntity.getDescription())
				.path(roomEntity.getPath())
				.author(AliasResponse.builder().username(roomEntity.getAuthor().getUsername())
						.profilePicture(roomEntity.getAuthor().getProfilePicture()).build())
				.posts(posts).created(timeService.toRelative(roomEntity.getCreateDate())).build();
	}

	private String generatePath(String title) {
		title = title.trim();
		final StringBuilder pathBuilder = new StringBuilder();
		final char[] titleChar = title.toCharArray();
		for (char c : titleChar) {
			if (Character.isAlphabetic(c) || Character.isDigit(c)) {
				pathBuilder.append(c);
			} else if (Character.isWhitespace(c)) {
				pathBuilder.append(Constants.HYPHEN);
			}
		}
		String path = pathBuilder.toString();
		String tempPath = path;
		int i = 1;
		while (roomRepository.countByPathAndActiveTrue(tempPath) > 0) {
			tempPath = path + "-" + i;
			i++;
		}
		path = tempPath;
		return path;
	}
}
package com.konoha.tsunade.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.konoha.tsunade.constants.ErrorCode;
import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.model.entity.AliasEntity;
import com.konoha.tsunade.model.entity.AnonymousUserEntity;
import com.konoha.tsunade.model.entity.RoomEntity;
import com.konoha.tsunade.model.entity.UserEntity;
import com.konoha.tsunade.repository.AliasRepository;
import com.konoha.tsunade.repository.AnonymousUserRepository;
import com.konoha.tsunade.service.AnonymousUserService;

@Service
public class AnonymousUserServiceImpl implements AnonymousUserService {

	@Autowired
	private AnonymousUserRepository anonymousUserRepository;

	@Autowired
	private AliasRepository aliasRepository;

	@Autowired
	private Random random;

	@Override
	public AnonymousUserEntity getAnonymousUser(final RoomEntity room, final UserEntity user, final boolean createMode)
			throws BaseBusinessException {
		if (room == null || user == null) {
			throw new BaseBusinessException(ErrorCode.INSUFFICIENT_PARAMETERS);
		}

		Optional<AnonymousUserEntity> optional = anonymousUserRepository.findByRoomAndUserAndActiveTrue(room, user);

		if (optional.isPresent()) {
			return optional.get();
		}
		return generateAnonymousUser(room, user);
	}

	private AnonymousUserEntity generateAnonymousUser(final RoomEntity room, final UserEntity user) {
		final List<AliasEntity> usedAliases = anonymousUserRepository.findByRoomAndActiveTrue(room).stream()
				.map(e -> e.getAlias()).collect(Collectors.toList());
		final List<AliasEntity> validAliases = aliasRepository.findAll();
		validAliases.removeAll(usedAliases);
		final AliasEntity alias = validAliases.get(random.nextInt(validAliases.size()));
		final AnonymousUserEntity anonymousUserEntity = AnonymousUserEntity.builder().room(room).user(user).alias(alias)
				.build();
		return anonymousUserRepository.save(anonymousUserEntity);
	}

}

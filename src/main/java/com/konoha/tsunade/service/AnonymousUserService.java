package com.konoha.tsunade.service;

import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.model.entity.AnonymousUserEntity;
import com.konoha.tsunade.model.entity.RoomEntity;
import com.konoha.tsunade.model.entity.UserEntity;

public interface AnonymousUserService {

	/**
	 * @param room       Room concerned
	 * @param user       User concerned
	 * @param createMode true if new user is to be created if mapping does not exist
	 * @return Anonymous User
	 * @throws BaseBusinessException
	 */
	AnonymousUserEntity getAnonymousUser(RoomEntity room, UserEntity user, boolean createMode)
			throws BaseBusinessException;
}

package com.konoha.tsunade.service;

import org.springframework.data.domain.Page;

import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.model.request.CreateRoomRequest;
import com.konoha.tsunade.model.request.UpdateRoomRequest;
import com.konoha.tsunade.model.response.RoomDetailResponse;
import com.konoha.tsunade.model.response.RoomResponse;

public interface RoomService {

	/**
	 * @param request  Create room request
	 * @param username User name of the person creating room
	 * @return Room response
	 * @throws BaseBusinessException
	 */
	RoomResponse createRoom(CreateRoomRequest request, String username) throws BaseBusinessException;

	/**
	 * @param request  Update room request
	 * @param path     Current path of the room
	 * @param username User name of the person updating room
	 * @return Room response
	 * @throws BaseBusinessException
	 */
	RoomResponse updateRoom(UpdateRoomRequest request, String username) throws BaseBusinessException;

	/**
	 * @param path     Path of the room
	 * @param pageNo   Page number to be displayed
	 * @param username User name
	 * @return Room details along with posts in it
	 * @throws BaseBusinessException
	 */
	RoomDetailResponse getRoomDetail(String path, Integer pageNo, String username) throws BaseBusinessException;

	/**
	 * @param pageNo The page number to be retrieved
	 * @return Page of Rooms
	 * @throws BaseBusinessException
	 */
	Page<RoomResponse> getRecentRoom(Integer pageNo) throws BaseBusinessException;

	/**
	 * @param username user name whose rooms are to be retrieved
	 * @param pageNo   page number
	 * @return page of rooms whose author user name is
	 * @throws BaseBusinessException
	 */
	Page<RoomResponse> getRoomsByUser(String username, Integer pageNo) throws BaseBusinessException;
}

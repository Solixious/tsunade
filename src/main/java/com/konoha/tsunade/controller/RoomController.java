package com.konoha.tsunade.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.konoha.tsunade.constants.ErrorCode;
import com.konoha.tsunade.constants.UrlMapping;
import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.model.request.CreateRoomRequest;
import com.konoha.tsunade.model.request.UpdateRoomRequest;
import com.konoha.tsunade.model.response.ErrorResponse;
import com.konoha.tsunade.service.RoomService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = UrlMapping.ROOM)
@Slf4j
public class RoomController {

	@Autowired
	private RoomService roomService;

	@PostMapping(value = UrlMapping.CREATE)
	public Object create(@RequestBody final CreateRoomRequest request, final Principal principal) {
		try {
			return roomService.createRoom(request, principal.getName());
		} catch (BaseBusinessException e) {
			log.error("Error while creating room, request: {}, errorCode: {}, errorDescription: {}", request,
					e.getErrorCode(), e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while creating room, request: {}, errorCode: {}, errorDescription: {}", request,
					ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}

	@PostMapping(value = UrlMapping.UPDATE)
	public Object update(@RequestBody final UpdateRoomRequest request, final Principal principal) {
		try {
			return roomService.updateRoom(request, principal.getName());
		} catch (BaseBusinessException e) {
			log.error("Error while updating room, request: {}, errorCode: {}, errorDescription: {}", request,
					e.getErrorCode(), e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while updating room, request: {}, errorCode: {}, errorDescription: {}", request,
					ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}

	@GetMapping(value = UrlMapping.PATH)
	public Object getRoomDetail(@PathVariable final String path, final Principal principal) {
		try {
			return roomService.getRoomDetail(path, 0, principal != null ? principal.getName() : null);
		} catch (BaseBusinessException e) {
			log.error("Error while getting room details, errorCode: {}, errorDescription: {}", e.getErrorCode(),
					e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while getting room details, errorCode: {}, errorDescription: {}",
					ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}

	@GetMapping(value = UrlMapping.PATH + UrlMapping.PAGE)
	public Object getRoomDetailPaged(@PathVariable final String path, @PathVariable final Integer pageNo,
			final Principal principal) {
		try {
			return roomService.getRoomDetail(path, pageNo, principal != null ? principal.getName() : null);
		} catch (BaseBusinessException e) {
			log.error("Error while getting room details, errorCode: {}, errorDescription: {}", e.getErrorCode(),
					e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while getting room details, errorCode: {}, errorDescription: {}",
					ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}

	@GetMapping(value = UrlMapping.RECENT)
	public Object getRecent() {
		try {
			return roomService.getRecentRoom(0);
		} catch (BaseBusinessException e) {
			log.error("Error while getting recent room, errorCode: {}, errorDescription: {}", e.getErrorCode(),
					e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while getting recent room, errorCode: {}, errorDescription: {}",
					ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}

	@GetMapping(value = UrlMapping.RECENT + UrlMapping.PAGE)
	public Object getRecentPaged(@PathVariable final Integer pageNo) {
		try {
			return roomService.getRecentRoom(pageNo);
		} catch (BaseBusinessException e) {
			log.error("Error while getting recent room, errorCode: {}, errorDescription: {}", e.getErrorCode(),
					e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while getting recent room, errorCode: {}, errorDescription: {}",
					ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}

	@GetMapping(value = UrlMapping.MY_ROOMS)
	public Object getUserRooms(final Principal principal) {
		try {
			return roomService.getRoomsByUser(principal.getName(), 0);
		} catch (BaseBusinessException e) {
			log.error("Error while getting room by user, errorCode: {}, errorDescription: {}", e.getErrorCode(),
					e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while getting recent room, errorCode: {}, errorDescription: {}",
					ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}

	@GetMapping(value = UrlMapping.MY_ROOMS + UrlMapping.PAGE)
	public Object getUserRoomsPaged(@PathVariable Integer pageNo, final Principal principal) {
		try {
			return roomService.getRoomsByUser(principal.getName(), pageNo);
		} catch (BaseBusinessException e) {
			log.error("Error while getting room by user, errorCode: {}, errorDescription: {}", e.getErrorCode(),
					e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while getting recent room, errorCode: {}, errorDescription: {}",
					ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}
}

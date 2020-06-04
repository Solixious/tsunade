package com.konoha.tsunade.service;

import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.model.request.AddReactionRequest;
import com.konoha.tsunade.model.request.RemoveReactionRequest;
import com.konoha.tsunade.model.response.ReactionResponse;

public interface ReactionService {

	/**
	 * @param request  Add reaction request
	 * @param username User name of the authorized user
	 * @return Reaction response
	 * @throws BaseBusinessException
	 */
	ReactionResponse addReaction(AddReactionRequest request, String username) throws BaseBusinessException;

	/**
	 * @param request  Remove reaction request
	 * @param username User name of the authorized user
	 * @return true if reaction is removed successfully
	 * @throws BaseBusinessException
	 */
	Boolean removeReaction(RemoveReactionRequest request, String username) throws BaseBusinessException;
}

package com.konoha.tsunade.service;

import org.springframework.data.domain.Page;

import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.model.request.CreatePostRequest;
import com.konoha.tsunade.model.response.PostResponse;

public interface PostService {

	/**
	 * @param request  Create post request
	 * @param username User name of the person creating post
	 * @return Post response
	 * @throws BaseBusinessException
	 */
	PostResponse createPost(CreatePostRequest request, String username) throws BaseBusinessException;

	/**
	 * @param path     Path of the room
	 * @param pageNo   Page number to retrieve
	 * @param username User name
	 * @return Page of post details in the room for given page number
	 * @throws BaseBusinessException
	 */
	Page<PostResponse> getPostsInRoom(String path, Integer pageNo, String username) throws BaseBusinessException;
}

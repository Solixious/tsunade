package com.konoha.tsunade.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.model.request.RegisterUserRequest;
import com.konoha.tsunade.model.request.ResetPasswordRequest;
import com.konoha.tsunade.model.request.UpdateUserRequest;
import com.konoha.tsunade.model.response.UserResponse;

public interface UserService extends UserDetailsService {

	/**
	 * @param userName User name
	 * @return true is the user name is valid
	 * @throws BaseBusinessException exception
	 */
	Boolean isValidUserName(String userName) throws BaseBusinessException;

	/**
	 * @param userName User name
	 * @return true if the user name exists
	 * @throws BaseBusinessException exception
	 */
	Boolean isExistingUserName(String userName) throws BaseBusinessException;

	/**
	 * @param email Email of the user
	 * @return true if the email passed as parameter is valid
	 * @throws BaseBusinessException exception
	 */
	Boolean isValidEmail(String email) throws BaseBusinessException;

	/**
	 * @param email Email of the user
	 * @return true if the email exists
	 * @throws BaseBusinessException exception
	 */
	Boolean isExistingEmail(String email) throws BaseBusinessException;

	/**
	 * @param password Password of the user
	 * @return true if the password is valid
	 * @throws BaseBusinessException exception
	 */
	Boolean isValidPassword(String password) throws BaseBusinessException;

	/**
	 * @param registerUserRequest user registration request
	 * @return registered user details
	 * @throws BaseBusinessException exception
	 */
	UserResponse save(RegisterUserRequest registerUserRequest) throws BaseBusinessException;

	/**
	 * @param username User name to be loaded
	 * @return The user details requested for
	 * @throws BaseBusinessException exception
	 */
	UserResponse load(String username) throws BaseBusinessException;

	/**
	 * @param updateUserRequest User request to be updated
	 * @return updated user response
	 * @throws BaseBusinessException exception
	 */
	UserResponse update(UpdateUserRequest updateUserRequest, String username) throws BaseBusinessException;

	/**
	 * @param resetPasswordRequest request for password reset
	 * @return true if reset password was successful
	 * @throws BaseBusinessException exception
	 */
	Boolean resetPassword(ResetPasswordRequest resetPasswordRequest) throws BaseBusinessException;

	/**
	 * @param file     File to be used for updating profile picture
	 * @param username User name for which the profile picture is to be updated
	 * @return true if updating profile picture update was successful
	 * @throws Exception exception
	 */
	Boolean updateProfilePicture(MultipartFile file, String username) throws Exception;
}
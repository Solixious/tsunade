package com.konoha.tsunade.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.konoha.tsunade.constants.Constants;
import com.konoha.tsunade.constants.ErrorCode;
import com.konoha.tsunade.constants.Role;
import com.konoha.tsunade.constants.SystemParameter;
import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.model.entity.RoleEntity;
import com.konoha.tsunade.model.entity.UserEntity;
import com.konoha.tsunade.model.request.RegisterUserRequest;
import com.konoha.tsunade.model.request.ResetPasswordRequest;
import com.konoha.tsunade.model.request.UpdateUserRequest;
import com.konoha.tsunade.model.response.UserResponse;
import com.konoha.tsunade.repository.RoleRepository;
import com.konoha.tsunade.repository.UserRepository;
import com.konoha.tsunade.service.FileStorageService;
import com.konoha.tsunade.service.SystemParameterService;
import com.konoha.tsunade.service.UserService;

import io.micrometer.core.instrument.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private SystemParameterService systemParameterService;

	@Override
	public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
		final UserEntity userEntity = userRepository.findByUserNameAndActiveTrue(userName)
				.orElseThrow(() -> new UsernameNotFoundException("User name '" + userName + "' not found."));
		return new User(userEntity.getUserName(), userEntity.getPassword(), getGrantedAuthorities(userEntity));
	}

	@Override
	public Boolean isValidUserName(final String userName) throws BaseBusinessException {
		if (StringUtils.isBlank(userName)
				|| userName.length() < systemParameterService
						.getSystemParameterInt(SystemParameter.MINIMUM_USERNAME_LENGTH)
				|| userName.length() > systemParameterService
						.getSystemParameterInt(SystemParameter.MAXIMUM_USERNAME_LENGTH)) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean isExistingUserName(final String userName) throws BaseBusinessException {
		if (!isValidUserName(userName)) {
			throw new BaseBusinessException(ErrorCode.INVALID_USER_NAME);
		}
		return userRepository.findByUserNameAndActiveTrue(userName).isPresent();
	}

	@Override
	public Boolean isValidEmail(final String email) {
		if (StringUtils.isBlank(email) || !Constants.EMAIL_PATTERN.matcher(email).matches()) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean isExistingEmail(final String email) throws BaseBusinessException {
		if (!isValidEmail(email)) {
			throw new BaseBusinessException(ErrorCode.INVALID_EMAIL_ID);
		}
		return userRepository.findByEmailAndActiveTrue(email).isPresent();
	}

	@Override
	public Boolean isValidPassword(final String password) throws BaseBusinessException {
		if (StringUtils.isBlank(password) || password.length() < systemParameterService
				.getSystemParameterInt(SystemParameter.MINIMUM_PASSWORD_LENGTH)) {
			return false;
		}
		return true;
	}

	@Override
	public UserResponse save(final RegisterUserRequest registerUserRequest) throws BaseBusinessException {
		if (isExistingUserName(registerUserRequest.getUsername())) {
			throw new BaseBusinessException(ErrorCode.USER_NAME_ALREADY_IN_USE);
		}
		if (isExistingEmail(registerUserRequest.getEmail())) {
			throw new BaseBusinessException(ErrorCode.EMAIL_ALREADY_IN_USE);
		}
		if (!isValidPassword(registerUserRequest.getPassword())) {
			throw new BaseBusinessException(ErrorCode.INVALID_PASSWORD);
		}

		final UserEntity userEntity = userRepository.save(
				UserEntity.builder().email(registerUserRequest.getEmail()).userName(registerUserRequest.getUsername())
						.password(passwordEncoder.encode(registerUserRequest.getPassword()))
						.roles(Arrays.asList(roleRepository.findByRoleNameAndActiveTrue(Role.USER.name()))).build());
		return convertUserEntityToUserResponse(userEntity);
	}

	@Override
	public UserResponse load(final String userName) throws BaseBusinessException {
		if (!isValidUserName(userName)) {
			throw new BaseBusinessException(ErrorCode.INVALID_USER_NAME);
		}
		final UserEntity userEntity = userRepository.findByUserNameAndActiveTrue(userName)
				.orElseThrow(() -> new BaseBusinessException(ErrorCode.USER_NOT_FOUND));
		return convertUserEntityToUserResponse(userEntity);
	}

	@Override
	public UserResponse update(final UpdateUserRequest updateUserRequest, final String username)
			throws BaseBusinessException {
		if (StringUtils.isBlank(username) || updateUserRequest == null) {
			throw new BaseBusinessException(ErrorCode.INSUFFICIENT_PARAMETERS);
		}

		final UserEntity userEntity = userRepository.findByUserNameAndActiveTrue(username)
				.orElseThrow(() -> new BaseBusinessException(ErrorCode.USER_NOT_FOUND));
		setUpdatedValues(updateUserRequest, userEntity);
		final UserEntity updatedUserEntity = userRepository.save(userEntity);
		return convertUserEntityToUserResponse(updatedUserEntity);
	}

	@Override
	public Boolean resetPassword(final ResetPasswordRequest resetPasswordRequest) throws BaseBusinessException {
		final UserEntity userEntity = userRepository.findByUserNameAndActiveTrue(resetPasswordRequest.getUsername())
				.orElseThrow(() -> new BaseBusinessException(ErrorCode.USER_NOT_FOUND));
		if (passwordEncoder.matches(resetPasswordRequest.getOldPassword(), userEntity.getPassword())
				&& isValidPassword(resetPasswordRequest.getNewPassword())) {
			userEntity.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
			userRepository.save(userEntity);
			return true;
		}
		return false;
	}

	@Override
	public Boolean updateProfilePicture(final MultipartFile file, final String username) throws Exception {
		final UserEntity userEntity = userRepository.findByUserNameAndActiveTrue(username)
				.orElseThrow(() -> new BaseBusinessException(ErrorCode.USER_NOT_FOUND));
		if (file != null && file.getOriginalFilename().length() > 0) {
			userEntity.setProfilePicture(ServletUriComponentsBuilder.fromCurrentContextPath().path("/file/")
					.path(fileStorageService.storeFile(file, username + " " + new Random().nextInt(9999) + " "
							+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'))))
					.toUriString());
			userRepository.save(userEntity);
			return true;
		}
		return false;
	}

	private UserResponse convertUserEntityToUserResponse(final UserEntity userEntity) throws BaseBusinessException {
		if (userEntity == null) {
			throw new BaseBusinessException(ErrorCode.NULL_USER);
		}
		return UserResponse.builder().email(userEntity.getEmail()).userName(userEntity.getUserName()).build();
	}

	private Collection<? extends GrantedAuthority> getGrantedAuthorities(UserEntity userEntity) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for (RoleEntity authority : userEntity.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(authority.getRoleName()));
		}
		return grantedAuthorities;
	}

	private void setUpdatedValues(final UpdateUserRequest updateUserRequest, final UserEntity userEntity)
			throws BaseBusinessException {
		final String newUsername = updateUserRequest.getUsername();
		if (StringUtils.isNotBlank(newUsername)) {
			if (!isValidUserName(newUsername)) {
				throw new BaseBusinessException(ErrorCode.INVALID_USER_NAME);
			} else if (!newUsername.equals(userEntity.getUserName()) && isExistingUserName(newUsername)) {
				throw new BaseBusinessException(ErrorCode.USER_NAME_ALREADY_IN_USE);
			} else {
				userEntity.setUserName(newUsername);
			}
		}

		final String newEmail = updateUserRequest.getEmail();
		if (StringUtils.isNotBlank(newEmail)) {
			if (!isValidEmail(newEmail)) {
				throw new BaseBusinessException(ErrorCode.INVALID_EMAIL_ID);
			} else if (!newEmail.equals(userEntity.getEmail()) && isExistingEmail(newEmail)) {
				throw new BaseBusinessException(ErrorCode.EMAIL_ALREADY_IN_USE);
			} else {
				userEntity.setEmail(newEmail);
			}
		}
	}
}
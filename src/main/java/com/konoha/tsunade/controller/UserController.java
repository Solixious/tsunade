package com.konoha.tsunade.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.konoha.tsunade.constants.ErrorCode;
import com.konoha.tsunade.constants.UrlMapping;
import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.model.request.GetTokenRequest;
import com.konoha.tsunade.model.request.RegisterUserRequest;
import com.konoha.tsunade.model.request.ResetPasswordRequest;
import com.konoha.tsunade.model.request.UpdateUserRequest;
import com.konoha.tsunade.model.response.ErrorResponse;
import com.konoha.tsunade.model.response.TokenResponse;
import com.konoha.tsunade.service.UserService;
import com.konoha.tsunade.util.TokenUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = UrlMapping.USER)
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenUtil tokenUtil;

	@PostMapping(value = UrlMapping.REGISTER)
	public Object register(@RequestBody final RegisterUserRequest request) {
		try {
			return userService.save(request);
		} catch (BaseBusinessException e) {
			log.error("Error while registering new user, request: {}, errorCode: {}, errorDescription: {}", request,
					e.getErrorCode(), e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while registering new user, request: {}, errorCode: {}, errorDescription: {}", request,
					ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}

	@PostMapping(value = UrlMapping.UPDATE)
	public Object updateUser(@RequestBody final UpdateUserRequest request, final Principal principal) {
		try {
			final String username = principal.getName();
			return userService.update(request, username);
		} catch (BaseBusinessException e) {
			log.error("Error while updating user, request: {}, errorCode: {}, errorDescription: {}", request,
					e.getErrorCode(), e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while updating user, request: {}, errorCode: {}, errorDescription: {}", request,
					ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}

	@PostMapping(value = UrlMapping.PROFILE_PICTURE)
	public Object updateProfilePicture(@RequestParam("file") final MultipartFile file, final Principal principal) {
		try {
			return userService.updateProfilePicture(file, principal.getName());
		} catch (Exception e) {
			log.error(
					"Error while updating profile picture, fileName: {}, fileSize: {}, fileContentTyoe: {}, userName: {}, errorCode: {}, errorDescription: {}",
					file.getName(), file.getSize(), file.getContentType(), principal.getName(),
					ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}

	@PostMapping(value = UrlMapping.TOKEN)
	public Object getToken(@RequestBody final GetTokenRequest request) {
		try {
			authenticate(request.getUsername(), request.getPassword());
			final UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
			final String token = tokenUtil.generateToken(userDetails);
			return new TokenResponse(token);
		} catch (UsernameNotFoundException e) {
			log.error("Error while getting token, request: {}, errorCode: {}, errorDescription: {}", request,
					ErrorCode.USER_NOT_FOUND.name(), ErrorCode.USER_NOT_FOUND.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.USER_NOT_FOUND.name())
					.errorDescription(ErrorCode.USER_NOT_FOUND.getDescription()).build();
		} catch (BaseBusinessException e) {
			log.error("Error while getting token, request: {}, errorCode: {}, errorDescription: {}", request,
					e.getErrorCode(), e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while getting token, request: {}, errorCode: {}, errorDescription: {}", request,
					ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}

	@PostMapping(value = UrlMapping.RESET_PASSWORD)
	public Object resetPassword(@RequestBody final ResetPasswordRequest request, final Principal principal) {
		try {
			request.setUsername(principal.getName());
			return userService.resetPassword(request);
		} catch (BaseBusinessException e) {
			log.error("Error while reset password, request: {}, errorCode: {}, errorDescription: {}", request,
					e.getErrorCode(), e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while reset password, request: {}, errorCode: {}, errorDescription: {}", request,
					ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}

	@GetMapping(value = UrlMapping.USERNAME_AVAILABILE)
	public Object usernameAvailability(@PathVariable final String userName) {
		try {
			return !userService.isExistingUserName(userName);
		} catch (BaseBusinessException e) {
			log.error("Error while fetching user availability, userName: {}, errorCode: {}, errorDescription: {}",
					userName, e.getErrorCode(), e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while fetching user availability, userName: {}, errorCode: {}, errorDescription: {}",
					userName, ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}

	@GetMapping(value = UrlMapping.EMAIL_AVAILABILE)
	public Object emailAvailability(@PathVariable final String email) {
		try {
			return !userService.isExistingEmail(email);
		} catch (BaseBusinessException e) {
			log.error("Error while fetching email availability, userName: {}, errorCode: {}, errorDescription: {}",
					email, e.getErrorCode(), e.getDescription(), e);
			return ErrorResponse.builder().errorCode(e.getErrorCode()).errorDescription(e.getDescription()).build();
		} catch (Exception e) {
			log.error("Error while fetching email availability, userName: {}, errorCode: {}, errorDescription: {}",
					email, ErrorCode.UNSPECIFIED.name(), ErrorCode.UNSPECIFIED.getDescription(), e);
			return ErrorResponse.builder().errorCode(ErrorCode.UNSPECIFIED.name())
					.errorDescription(ErrorCode.UNSPECIFIED.getDescription()).build();
		}
	}

	@GetMapping
	public Object getHello(final Principal principal) {
		return "Hello " + principal.getName() + "!";
	}

	private void authenticate(final String username, final String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}

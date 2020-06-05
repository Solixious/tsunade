package com.konoha.tsunade.constants;

public enum ErrorCode {

	UNSPECIFIED("Unspecified error."), INVALID_USER_NAME("Invalid Username."),
	INVALID_EMAIL_ID("Email must not be blank or null and must be a valid email."),
	INVALID_PASSWORD("Invalid Password."), USER_NAME_ALREADY_IN_USE("User name is already in use."),
	EMAIL_ALREADY_IN_USE("Email is already in use."), USER_NOT_FOUND("User does not exist."),
	NULL_USER("User Entity cannot be null."),
	INSUFFICIENT_PARAMETERS("Parameters required for requested operation is insufficient."),
	FILE_STORAGE_EXCEPTION("Exception while storing the file."), NO_ALIASES_FOUND("No aliases found."),
	ROOM_NOT_FOUND("Room being retrieved could not be found."),
	UNAUTHORIZED("The user is unauthorized to perform the requested action."),
	SYSTEM_PARAMETER_NOT_FOUND("System parameter not found."), POST_NOT_FOUND("No posts found."),
	POST_AND_ROOM_MISMATCH("Post and room mismatch in request"), ANONYMOUS_USER_NOT_FOUND("Anonymous user not found."),
	REACTION_NOT_FOUND("Reaction not found."), FEEDBACK_NOT_FOUND("Feedback not found.");

	String description;

	ErrorCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}

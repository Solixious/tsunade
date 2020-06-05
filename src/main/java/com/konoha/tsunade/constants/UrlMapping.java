package com.konoha.tsunade.constants;

public interface UrlMapping {

	String USER = "/user";
	String ADMIN = "/admin";
	String REGISTER = "/register";
	String TOKEN = "/token";
	String RESET_PASSWORD = "/reset-password";
	String PROFILE_PICTURE = "/profile-picture";
	String DOWNLOAD_FILE = "/file/{fileName:.+}";
	String ALIAS = "/alias";
	String UPDATE = "/update";
	String USERNAME_AVAILABILE = "/available/username/{userName}";
	String EMAIL_AVAILABILE = "/available/email/{email}";
	String ROOM = "/room";
	String POST = "/post";
	String CREATE = "/create";
	String USER_NAME = "/{userName}";
	String PAGE = "/page/{pageNo}";
	String RECENT = "/recent";
	String PATH = "/{path}";
	String MY_ROOMS = "/my-rooms";
	String REACTION = "/reaction";
	String FEEDBACK = "/feedback";
}

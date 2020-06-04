package com.konoha.tsunade.constants;

import java.util.regex.Pattern;

public interface Constants {

	String HYPHEN = "-";
	String TOKEN_PREFIX = "Bearer ";
	String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
	String USER_NAME_PATTERN = "^[a-zA-Z0-9_]{3,}$";
	String UNAUTHENTICATED = "unauthenticated";
	Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
}
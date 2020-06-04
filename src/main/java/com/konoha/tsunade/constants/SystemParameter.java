package com.konoha.tsunade.constants;

public enum SystemParameter {

	MINIMUM_PASSWORD_LENGTH("4"), MINIMUM_USERNAME_LENGTH("4"), MAXIMUM_USERNAME_LENGTH("32"),
	PAGE_SIZE_ALIAS_LIST("50"), PAGE_SIZE_RECENT_ROOMS("50"), PAGE_SIZE_POSTS("10"), PAGE_SIZE_USER_ROOMS("24"),
	TIME_DISPLAY_LEVEL("1");

	private String defaultValue;

	private SystemParameter(final String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}
}

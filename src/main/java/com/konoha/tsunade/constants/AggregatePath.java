package com.konoha.tsunade.constants;

public interface AggregatePath {

	String[] PUBLIC = { UrlMapping.USER + UrlMapping.REGISTER, UrlMapping.USER + UrlMapping.TOKEN,
			UrlMapping.USER + UrlMapping.USERNAME_AVAILABILE, UrlMapping.USER + UrlMapping.EMAIL_AVAILABILE,
			UrlMapping.ROOM + UrlMapping.PATH, UrlMapping.ROOM + UrlMapping.PATH + UrlMapping.PAGE,
			UrlMapping.FEEDBACK };

	String[] ADMIN = { UrlMapping.ADMIN_ALL };
}

package com.konoha.tsunade.constants;

public interface ColumnName {

	// Base Entity
	String ID = "tsu_column_id";
	String CREATE_DATE = "tsu_column_create_date";
	String UPDATE_DATE = "tsu_column_update_date";
	String CREATED_BY = "tsu_column_created_by";
	String UPDATED_BY = "tsu_column_updated_by";
	String ACTIVE = "tsu_column_active";

	// User Entity
	String USER_NAME = "tsu_column_user_name";
	String EMAIL = "tsu_column_email";
	String PASSWORD = "tsu_column_password";
	String FORCE_RESET_PASSWORD = "tsu_column_force_reset_password";
	String RESET_PASSWORD_KEY = "tsu_column_reset_password_key";
	String PROFILE_PICTURE = "tsu_column_profile_picture";

	// Role Entity
	String ROLE_NAME = "tsu_column_role_name";

	// User Role
	String USER_ID = "tsu_column_user_id";
	String ROLE_ID = "tsu_column_role_id";

	// Room Entity
	String TITLE = "tsu_column_title";
	String DESCRIPTION = "tsu_column_description";
	String PATH = "tsu_column_path";
	String CLOSED = "tsu_column_closed";

	// Post Entity
	String CONTENT = "tsu_column_content";

	// Anonymous User Entity
	String ROOM_ID = "tsu_column_room_id";
	String ALIAS_ID = "tsu_column_alias_id";

	// System Parameter
	String VARIABLE = "tsu_column_variable";
	String VALUE = "tsu_column_value";

	// Reaction Entity
	String POST_ID = "tsu_column_post_id";
	String ANONYMOUS_USER_ID = "tsu_column_anonymous_user_id";
	String REACTION_TYPE = "tsu_column_reaction_type";

	// Feedback Entity
	String FEEDBACK = "tsu_column_feedback";
	String STATUS = "tsu_column_status";
}

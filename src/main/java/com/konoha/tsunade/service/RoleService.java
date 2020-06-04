package com.konoha.tsunade.service;

public interface RoleService {

	/**
	 * @param name Role to be retrieved
	 * @return Role name
	 */
	String getRole(String name);

	/**
	 * @param name Role to be added
	 */
	void addRole(String name);
}
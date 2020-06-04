package com.konoha.tsunade.service;

import java.util.Date;

import com.konoha.tsunade.exceptions.BaseBusinessException;

public interface TimeService {

	/**
	 * @param date Date to process
	 * @return Time ago in string format
	 * @throws BaseBusinessException
	 */
	String toRelative(Date date);
}

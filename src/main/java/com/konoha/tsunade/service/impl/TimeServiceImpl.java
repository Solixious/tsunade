package com.konoha.tsunade.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.konoha.tsunade.constants.SystemParameter;
import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.service.SystemParameterService;
import com.konoha.tsunade.service.TimeService;
import com.konoha.tsunade.util.TimeUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TimeServiceImpl implements TimeService {

	@Autowired
	private SystemParameterService systemParameterService;

	@Override
	public String toRelative(final Date date) {
		try {
			return TimeUtil.toRelative(date,
					systemParameterService.getSystemParameterInt(SystemParameter.TIME_DISPLAY_LEVEL));
		} catch (BaseBusinessException e) {
			log.error("Unknown error occurred while converting date.");
			return "Unknown Error Occurred";
		}
	}

}

package com.konoha.tsunade.config;

import java.util.Random;
import java.util.regex.Pattern;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.konoha.tsunade.constants.Constants;

@Component
public class BeanConfig {

	@Bean
	public Mapper mapper() {
		return new DozerBeanMapper();
	}

	@Bean
	public Pattern userNamePattern() {
		return Pattern.compile(Constants.USER_NAME_PATTERN);
	}

	@Bean
	public Random random() {
		return new Random();
	}
}

package com.konoha.tsunade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TsunadeApplication {

	public static void main(final String[] args) {
		SpringApplication.run(TsunadeApplication.class, args);
	}
}

package com.konoha.tsunade.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.konoha.tsunade.constants.Constants;

@Component
public class SystemLoggedInUserAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return Optional.of(auth != null ? auth.getName() : Constants.UNAUTHENTICATED);
	}
}

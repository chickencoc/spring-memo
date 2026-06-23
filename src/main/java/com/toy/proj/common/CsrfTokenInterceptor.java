package com.toy.proj.common;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Set;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CsrfTokenInterceptor implements HandlerInterceptor {

	private static final Set<String> SAFE_METHODS = Set.of("GET", "HEAD", "OPTIONS", "TRACE");
	private final SecureRandom secureRandom = new SecureRandom();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		if(SAFE_METHODS.contains(request.getMethod())) {
			request.setAttribute(ComField.CSRF_TOKEN, ensureToken(request));
			return true;
		}

		String sessionToken = getSessionToken(request);
		String requestToken = request.getHeader(ComField.CSRF_HEADER_NAME);

		if(!StringUtils.hasLength(requestToken)) {
			requestToken = request.getParameter(ComField.CSRF_PARAMETER_NAME);
		}

		if(!StringUtils.hasLength(sessionToken) || !sessionToken.equals(requestToken)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF token.");
			return false;
		}

		request.setAttribute(ComField.CSRF_TOKEN, sessionToken);
		return true;
	}

	private String ensureToken(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Object token = session.getAttribute(ComField.CSRF_TOKEN);

		if(token instanceof String tokenValue && StringUtils.hasLength(tokenValue)) {
			return tokenValue;
		}

		String newToken = createToken();
		session.setAttribute(ComField.CSRF_TOKEN, newToken);
		return newToken;
	}

	private String getSessionToken(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if(session == null) {
			return null;
		}

		Object token = session.getAttribute(ComField.CSRF_TOKEN);
		return token instanceof String ? (String) token : null;
	}

	private String createToken() {
		byte[] bytes = new byte[32];
		secureRandom.nextBytes(bytes);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	}
}

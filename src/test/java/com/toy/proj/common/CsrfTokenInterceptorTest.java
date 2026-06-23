package com.toy.proj.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class CsrfTokenInterceptorTest {

	private final CsrfTokenInterceptor interceptor = new CsrfTokenInterceptor();

	@Test
	void createsTokenForSafeMethod() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/memo/list");
		MockHttpServletResponse response = new MockHttpServletResponse();

		boolean result = interceptor.preHandle(request, response, null);
		Object token = request.getSession().getAttribute(ComField.CSRF_TOKEN);

		assertThat(result).isTrue();
		assertThat(token).isInstanceOf(String.class);
		assertThat((String) token).hasSizeGreaterThan(30);
	}

	@Test
	void rejectsUnsafeMethodWithoutToken() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/memo/save");
		MockHttpServletResponse response = new MockHttpServletResponse();

		boolean result = interceptor.preHandle(request, response, null);

		assertThat(result).isFalse();
		assertThat(response.getStatus()).isEqualTo(403);
	}

	@Test
	void allowsUnsafeMethodWithHeaderToken() throws Exception {
		MockHttpServletRequest getRequest = new MockHttpServletRequest("GET", "/memo/list");
		interceptor.preHandle(getRequest, new MockHttpServletResponse(), null);
		String token = (String) getRequest.getSession().getAttribute(ComField.CSRF_TOKEN);

		MockHttpServletRequest postRequest = new MockHttpServletRequest("POST", "/api/memo/save");
		postRequest.setSession(getRequest.getSession());
		postRequest.addHeader(ComField.CSRF_HEADER_NAME, token);
		MockHttpServletResponse response = new MockHttpServletResponse();

		boolean result = interceptor.preHandle(postRequest, response, null);

		assertThat(result).isTrue();
		assertThat(response.getStatus()).isEqualTo(200);
	}
}

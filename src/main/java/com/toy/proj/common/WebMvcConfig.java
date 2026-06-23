package com.toy.proj.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.toy.proj.login.LoginIntercetor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(loginInterceptor()).addPathPatterns("/memo/**", "/api/memo/**", "/api/mlog/**");
		registry.addInterceptor(csrfTokenInterceptor()).addPathPatterns("/memo/**", "/api/**");
	}

	@Bean
	LoginIntercetor loginInterceptor() {
		return new LoginIntercetor();
	}

	@Bean
	CsrfTokenInterceptor csrfTokenInterceptor() {
		return new CsrfTokenInterceptor();
	}
}

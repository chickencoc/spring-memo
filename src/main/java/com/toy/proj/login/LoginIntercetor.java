package com.toy.proj.login;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.toy.proj.common.ComField;
import com.toy.proj.member.model.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginIntercetor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		HttpSession ses = request.getSession();
		Member member = (Member) ses.getAttribute( ComField.SES_USER );
		
		if( member == null || member.getStatus().equals( ComField.MEM_STATUS_GUEST ) ) {
			
			String ip = request.getHeader("X-FORWARDED-FOR");
	    	
	    	if(ip == null) {
	    		ip = request.getRemoteAddr();
	    	}
			
	    	String s = ( (Long) (System.currentTimeMillis() / 1000L) ).toString();
	    	
	    	member = Member.getGuestMember(s);
	    	ses.setAttribute( ComField.SES_USER, member );
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}

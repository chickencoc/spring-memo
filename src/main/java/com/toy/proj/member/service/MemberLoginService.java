package com.toy.proj.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.toy.proj.common.CodeField;
import com.toy.proj.common.ComField;
import com.toy.proj.common.ComUtil;
import com.toy.proj.member.model.Member;
import com.toy.proj.member.repository.MemberRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberLoginService {
	
	private final MemberRepository memberRepository;
	
	public String auth(HttpServletRequest request, String uid, String upw) {
		
		if( StringUtils.hasLength(uid) && StringUtils.hasLength(upw) ) {
			Optional<Member> optMem = memberRepository.findById(uid);
			
			if( optMem.isPresent() ) {
				Member member = optMem.get();
				
				if( member.getUseYn() == 'N') {
					return CodeField.ERR_LOGIN_USEYN;
				}
				
				if( member.getUpw().equals( ComUtil.toSha256String( upw, getPwdSalt(member.getUpw()) ) ) ) {
					if(request.getSession(false) != null) {
						request.changeSessionId();
					}
					request.getSession().setAttribute(ComField.SES_USER, member);
					return CodeField.SUCCESS;
				}
			}
		}
		
		return CodeField.FAIL;
	}
	
	private String getPwdSalt(String pwd) {
		String[] arr = pwd.split("\\.");
		
		if(arr.length == 2) {
			return arr[1];
		}
		
		return "";
	}
}

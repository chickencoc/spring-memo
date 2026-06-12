package com.toy.proj.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.toy.proj.common.ComField;
import com.toy.proj.member.model.Member;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/memo")
public class MemberLoginViewController {

	@GetMapping("/login")
	public String memberLogin(HttpSession ses) {
		
		Member member = (Member) ses.getAttribute( ComField.SES_USER );
		
		if(member != null && !member.getStatus().equals( ComField.MEM_STATUS_GUEST )) {
			return "redirect:/memo/list";
		}
		
		return "login";
	}
}

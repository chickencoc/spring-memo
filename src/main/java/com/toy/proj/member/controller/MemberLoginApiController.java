package com.toy.proj.member.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toy.proj.common.model.ResponseDto;
import com.toy.proj.member.service.MemberLoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class MemberLoginApiController {

	private final MemberLoginService memberLoginService;
	
	@PostMapping("/auth")
	public ResponseDto auth(HttpServletRequest request, @RequestBody HashMap<String, String> map) {
		
//		GsonJsonParser jp = new GsonJsonParser();
//		String json = new String( Base64.getDecoder().decode(map.get("userData")) );
//		Map<String, Object> jmap = jp.parseMap(json);
		
		String result = memberLoginService.auth(request, (String) map.get("uid"), (String) map.get("upw"));
		
		return ResponseDto.code(result).build();
	}
	
	@GetMapping("/expire")
	public boolean logout(HttpSession session) {
		session.invalidate();
		
		return true;
	}
}

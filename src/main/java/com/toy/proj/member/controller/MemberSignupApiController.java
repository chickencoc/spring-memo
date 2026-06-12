package com.toy.proj.member.controller;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.toy.proj.common.ComUtil;
import com.toy.proj.member.model.Member;
import com.toy.proj.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberSignupApiController {
	
	private final MemberRepository memberRepository;
	
	@PostMapping("/api/m/c/t")
	public ResponseEntity<Member> cm(@RequestBody HashMap<String, String> map) {

		Member result = null;
		Optional<Member> optMem = memberRepository.findById(map.get("id"));
		
		if(optMem.isEmpty()) {
			Member tm = Member.builder()
						.uid(map.get("id"))
						.upw( ComUtil.toSha256String( map.get("password") ) )
						.email( map.get("email") )
						.build();
//			Member tm = new Member();
			result = memberRepository.save(tm);
		}
		
		return ResponseEntity.ok(result);
	}


}

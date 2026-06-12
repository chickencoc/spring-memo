package com.toy.proj.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toy.proj.member.model.Member;

public interface MemberRepository extends JpaRepository<Member, String>{

	Optional<Member> findByUidAndUseYnLike(String uid, char useYn);
	
}

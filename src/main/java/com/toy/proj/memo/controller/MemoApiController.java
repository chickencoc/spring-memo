package com.toy.proj.memo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toy.proj.common.ComField;
import com.toy.proj.member.model.Member;
import com.toy.proj.memo.model.Memo;
import com.toy.proj.memo.service.MemoService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memo")
public class MemoApiController {

    private final MemoService memoService;

    // url : api/memo/save
    @PostMapping("/save")
    public ResponseEntity<Memo> createMemo(HttpServletRequest request, @RequestBody Memo memoDto) {
    	
    	Member member = (Member) request.getSession().getAttribute( ComField.SES_USER );

        memoDto.setCrmid(member.getUid());
        memoDto.setUpmid(member.getUid());
    	
    	Memo newMemo = memoService.createMemo(memoDto);
    	return ResponseEntity.ok(newMemo);
    }

    // url : api/memo/update TODO
    @PostMapping("/update")
    public ResponseEntity<?> updatememo(HttpServletRequest request, @RequestBody Memo memoDto) {
    	
    	Member member = (Member) request.getSession().getAttribute( ComField.SES_USER );
    	
    	boolean chkAuth = memoService.checkAuthority(member, memoDto);
    	
    	if(!chkAuth) {
    		return ResponseEntity.ok(new Memo());
    	}
    	
        memoDto.setCrmid(member.getUid());
        memoDto.setUpmid(member.getUid());
    	
    	Memo newMemo = memoService.updateMemo(memoDto);
    	return ResponseEntity.ok(newMemo);
    }

    // url : api/memo/delete/{memoNo} TODO
    @DeleteMapping("/delete/{memoNo}")
    public ResponseEntity<Boolean> deletememo(@PathVariable Long memoNo) {
    	return null;
    }

    // url : api/memo/all
    @GetMapping("/all")
    public ResponseEntity<List<Memo>> getAllMemo() {
    	return ResponseEntity.ok(memoService.getAllMemo());
    }

    // url : api/memo/view/{serial}
    @GetMapping("/view/{seq}")
    public ResponseEntity<Memo> getmemo(@PathVariable Integer seq) {
    	return ResponseEntity.ok(memoService.getMemo(seq));
    }

    // url : api/memo/search?q=something
    @GetMapping("/search")
    public ResponseEntity<List<Memo>> searchmemo(@RequestParam("q") String keyword) {
    	return ResponseEntity.ok(memoService.searchMemo(keyword));
    }

}

package com.toy.proj.memo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toy.proj.memo.model.MemoLog;
import com.toy.proj.memo.service.MemoLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mlog")
public class MemoLogApiController {
	
	private final MemoLogService memoLogService;

    // url : api/mlog/{memoSeq}
    @GetMapping("/{memoSeq}")
    public ResponseEntity<List<MemoLog>> getAllMemo(@PathVariable int memoSeq) {
    	
    	return ResponseEntity.ok(memoLogService.getAllMemoLogByMemoSeq(memoSeq));
    }
}

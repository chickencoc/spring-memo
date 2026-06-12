package com.toy.proj.memo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.toy.proj.memo.model.MemoLog;
import com.toy.proj.memo.repository.MemoLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemoLogService {

    private final MemoLogRepository memoLogRepository;
	
    public List<MemoLog> getAllMemoLogByMemoSeq(int memoSeq) {
    	
    	return memoLogRepository.findByMemoSeqOrderByCrdteDesc(memoSeq);
    	
    }
}

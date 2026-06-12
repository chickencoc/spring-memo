package com.toy.proj.memo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toy.proj.memo.model.MemoLog;

@Repository
public interface MemoLogRepository extends JpaRepository<MemoLog, Integer> {
	
	List<MemoLog> findByMemoSeqOrderByCrdteDesc(int memoSeq);
}

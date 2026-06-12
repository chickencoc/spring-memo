package com.toy.proj.memo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.toy.proj.memo.model.Memo;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Integer> {

	@Query("SELECT m FROM Memo m WHERE m.title LIKE %:keyword% OR m.content LIKE %:keyword% ORDER BY m.crdte DESC")
	List<Memo> getListOfMemo(@Param("keyword") String keyword);
	
	List<Memo> findByTitleLikeOrContentLikeOrderByCrdteDesc(String keyword1, String keyword2);
	
	List<Memo> findByOrderByCrdteDesc();

	List<Memo> findByCrmidLikeOrderByCrdteDesc(String uid);
	
	Optional<Memo> findBySerial(String serial);
}

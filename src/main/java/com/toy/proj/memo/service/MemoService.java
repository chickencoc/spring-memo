package com.toy.proj.memo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.toy.proj.common.ComField;
import com.toy.proj.member.model.Member;
import com.toy.proj.member.repository.MemberRepository;
import com.toy.proj.memo.model.Memo;
import com.toy.proj.memo.repository.MemoLogRepository;
import com.toy.proj.memo.repository.MemoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final MemoLogRepository memoLogRepository;
    private final MemberRepository memberRepository;
    

    // 메모 등록 Create
    @Transactional
    public Memo createMemo(Memo memoDto) {
    		return memoRepository.save(memoDto);
    }

    // 메모 수정 Update TODO
    @Transactional
    public Memo updateMemo(Memo memoDto) {
    	// 기존 메모를 수정한 것인지 시리얼 일치를 통해 확인
    	Optional<Memo> optMemo = memoRepository.findById(memoDto.getSeq());

    	if(optMemo.isPresent()) {
            // 수정한 메모일 경우
    		Memo oldMemo = optMemo.get();
            // 기존 메모를 log로 만들고 수정된 메모의 작성자를 log의 작성자로 입력, DB 반영
    		memoLogRepository.save( oldMemo.toLog( memoDto.getCrmid() ) );
    		
    		// 기존 메모 업데이트 및 dirty checking으로 DB 반영
    		return oldMemo.update(memoDto);

    	} 

    	return null;
    }

    // 메모 삭제 Delete TODO
    public Boolean deleteMemo(int seq) {
        return true;
    }

    // 메모 전체 목록 ReadAll
    public List<Memo> getAllMemo() {
        return memoRepository.findByOrderByCrdteDesc();
    }

    // 메모 상세 내용 Detail
    public Memo getMemo(Integer seq) {
        Optional<Memo> optMemo = memoRepository.findById(seq);

        return optMemo.orElseGet(() -> {
            Memo memo = new Memo();
            memo.setTitle("존재하지 않는 메모");
            memo.setContent("존재하지 않는 메모입니다. 다시 시도해주세요.");
            memo.setSerial("");
            return memo;
        });
    }

    // 메모 수정 로그 TODO
    public List<Memo> getMemoHistory(String keyword) {
        return null;
    }
    
    // 메모 검색 Search
    public List<Memo> searchMemo(String keyword) {
        return memoRepository.getListOfMemo(keyword);
    }
    
    public boolean canEditMemo(Member member, Integer memoSeq) {
		if(memoSeq == null) {
			return false;
		}

		return memoRepository.findById(memoSeq)
				.map(memo -> canEditMemo(member, memo))
				.orElse(false);
    }

    public boolean canEditMemo(Member member, Memo memo) {
		if(member == null || memo == null) {
			return false;
		}

		if(ComField.MEM_STATUS_ADMIN.equals(member.getStatus())) {
			return true;
		}

		return member.getUid() != null && member.getUid().equals(memo.getCrmid());
    }

    public boolean checkAuthority(Member member, Memo memoDto) {
		return memoDto != null && canEditMemo(member, memoDto.getSeq());
    }

    public String getMemberDisplayName(String uid) {
		if(!StringUtils.hasText(uid)) {
			return "";
		}

		if(uid.startsWith(ComField.GUEST_UID_PREFIX)) {
			return "Guest";
		}

		return memberRepository.findById(uid)
				.map(Member::getDisplayName)
				.orElse(uid);
    }

}

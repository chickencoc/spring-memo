package com.toy.proj.memo.model;

import com.toy.proj.common.ComUtil;
import com.toy.proj.common.model.timeEntity.TimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Memo extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq; // seq는 안바뀜 serial은 바뀜 그래서 seq가 있어야함
    private String serial;
    private String title;
    private String content;
    private Boolean locked;
    private Integer dirSeq;

	public Memo() {
		serial = ComUtil.getSaltString();
		dirSeq = 1;
	}
	
    public MemoLog toLog(String logCrmid) {
    	return MemoLog.builder()
    			.serial(serial)
    			.title(title)
    			.content(content)
    			.memoSeq(seq)
    			// 업데이트 내역이 없으면 생성 mid, 일자로 로그 생성
    			.orgmid(crmid)
    			.orgdte(crdte)
				.crmid(upmid)
				.upmid(logCrmid)
    			.build();
    }
    
    public Memo update(Memo memoDto) {
    	serial = ComUtil.getSaltString();
    	title = memoDto.getTitle();
    	content = memoDto.getContent();
    	locked = memoDto.getLocked();
    	dirSeq = memoDto.getDirSeq();
		upmid = memoDto.getCrmid();
    	
    	return this;
    }
}




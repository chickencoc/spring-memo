package com.toy.proj.memo.model;

import com.toy.proj.common.model.timeEntity.TimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class MemoLog extends TimeEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int seq;
	private String serial;
    private String title;
    private String content;
    private int memoSeq;
    private String orgmid;
    private LocalDateTime orgdte;

    @Builder
    public MemoLog(int seq, String serial, String title, String content, int memoSeq, String orgmid, LocalDateTime orgdte, String crmid, String upmid) {
        this.seq = seq;
        this.serial = serial;
        this.title = title;
        this.content = content;
        this.memoSeq = memoSeq;
        this.orgmid = orgmid;
        this.orgdte = orgdte;
        this.crmid = crmid;
        this.upmid = upmid;
    }
}
package com.toy.memo.model.timeEntity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CommonEntity implements TimeAuditable {

	protected String crmid;
    @CreatedDate
    protected LocalDateTime crdte;
    protected String upmid;
    @LastModifiedDate
    protected LocalDateTime updte;
}

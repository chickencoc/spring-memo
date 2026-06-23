package com.toy.proj.member.model;

import com.toy.proj.common.ComField;
import com.toy.proj.common.model.timeEntity.TimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Member extends TimeEntity {

    @Id
    @NotBlank
    private String uid;
    @NotBlank
    private String upw;
    @NotBlank
    private String uname;
    @NotBlank
    private String email;
    private String team; // teamA,teamB,teamC....
    private String status = "NNNNN"; // YYYYY, NNNNN, AAAAA....
    private char useYn = 'N';
    @Transient
    private char[] statusCharArray;
    
    @Builder
	public Member(@NotBlank String uid, @NotBlank String uname, @NotBlank String upw, @NotBlank String email) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.upw = upw;
		this.email = email;
		this.useYn = 'N';
		this.team = "NONE";
	}
    
    public static Member getGuestMember(String uid, String uname) {
		Member m = new Member();
		m.setUid(uid);
		m.setUname(uname);
		m.setUseYn('Y');
		m.setStatus(ComField.MEM_STATUS_GUEST);
		return m;
    }

    public String getDisplayName() {
		return uname == null || uname.isBlank() ? uid : uname;
    }
    
    
}

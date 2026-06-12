package com.toy.proj.common.model;

import com.toy.proj.common.CodeField;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseDto {
	
	private Object data;
	private String code;
	
	public static Builder success() {
		return new Builder(CodeField.SUCCESS);
	}
	
	public static Builder fail() {
		return new Builder(CodeField.FAIL);
	}
	
	public static Builder code(String code) {
		return new Builder(code);
	}
	
	public static class Builder {
		
		private String code;
		
		public Builder(String code) {
			this.code = code;
		}
		
		public ResponseDto data(Object data) {
			return new ResponseDto(data, this.code);
		}
		
		public ResponseDto build() {
			return new ResponseDto(null, this.code);
		}
	}
	
}

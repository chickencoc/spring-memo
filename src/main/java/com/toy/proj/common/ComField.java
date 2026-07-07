package com.toy.proj.common;

public interface ComField {

	public static String SES_USER = "SES_USER";
	public static String CSRF_TOKEN = "CSRF_TOKEN";
	public static String CSRF_HEADER_NAME = "X-CSRF-TOKEN";
	public static String CSRF_PARAMETER_NAME = "_csrf";
	
	public static String MEM_STATUS_ADMIN = "ADMIN";
	public static String MEM_STATUS_USER = "USERS";
	public static String MEM_STATUS_GUEST = "GUEST";
	public static String GUEST_UID_PREFIX = "guest-";
	
}

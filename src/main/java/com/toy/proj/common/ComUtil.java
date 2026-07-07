package com.toy.proj.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public interface ComUtil {

	public static String getSaltString(int x) {
		String SALTCHARS = "1234567890abcdefghijklmnopqrstuvwxyz";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();

		while (salt.length() < x) { // length of the random string.

			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}

		return salt.toString();
	}

	public static String getSaltString() {
		return getSaltString(7);
	}
	
	public static String toSha256String(String value) {
		return toSha256String(value, null);
	}
	
	public static String toSha256String(String value, String salt) {
		try {
			String mascot = ".ducking_pepper";
			
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			
			// pepper salt chap chap
			String ppap = salt.isBlank() ? getSaltString(16) : salt;
			value += mascot + ppap;				
			
			
			byte[] hashBytes = digest.digest(value.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			
			for(byte b : hashBytes) {
				sb.append(String.format("%02x", b));
			}
			
			sb.append(".").append(ppap);
			
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("SHA-256 algorithm not found.");
		}
	}

}
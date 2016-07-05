package com.hoyoung.fortis.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Md5Util {

	public static String toMd5(String data) throws NoSuchAlgorithmException {

		MessageDigest md5;
		md5 = MessageDigest.getInstance("MD5");
		md5.update(data.getBytes());
		byte[] digest = md5.digest();
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < digest.length; i++) {
			int b = digest[i] & 0xFF;
			if (b < 0x10)
				sb.append('0');
			sb.append(Integer.toHexString(b));

		}

		return sb.toString();

	}
}

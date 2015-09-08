package com.carinsurance.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	public static String getMD5(String str) {
		StringBuffer hexString = new StringBuffer();
		if (str != null && str.trim().length() != 0) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(str.getBytes());
				byte[] hash = md.digest();
				for (int i = 0; i < hash.length; i++) {
					if ((0xff & hash[i]) < 0x10) {
						hexString.append("0"
								+ Integer.toHexString((0xFF & hash[i])));
					} else {
						hexString.append(Integer.toHexString(0xFF & hash[i]));
					}
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return hexString.toString();
	}
}

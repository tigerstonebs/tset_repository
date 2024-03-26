package com.jungstagram.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Util {
	
	public static String insertToken() {
		String token = null;
	    SecureRandom secureRandom;
	    try {
	        secureRandom = SecureRandom.getInstance("SHA1PRNG");
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        secureRandom.setSeed(secureRandom.generateSeed(128));
	        token= new String(digest.digest((secureRandom.nextLong() + "").getBytes()));
	    } catch (NoSuchAlgorithmException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return token;
	}

}

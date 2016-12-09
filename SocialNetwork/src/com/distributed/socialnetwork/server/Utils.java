package com.distributed.socialnetwork.server;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;

import javax.xml.bind.DatatypeConverter;

public class Utils {

	public static final DateFormat DATE_FORMAT  = new SimpleDateFormat("yyyy/MM/dd");
	public static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
	
	public static String encode(String password) {
		try {
			return Base64.getEncoder().encodeToString(password.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
		}
		return "";
	}
	
	public static byte[] decode(String bytes) {
		return Base64.getDecoder().decode(bytes);
	}
}

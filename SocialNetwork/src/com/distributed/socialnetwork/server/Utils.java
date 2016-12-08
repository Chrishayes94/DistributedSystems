package com.distributed.socialnetwork.server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.xml.bind.DatatypeConverter;

public class Utils {

	public static final DateFormat DATE_FORMAT  = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	
	public static byte[] encode(String password) {
		return DatatypeConverter.parseBase64Binary(password);
	}
}

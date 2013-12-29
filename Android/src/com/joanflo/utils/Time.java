package com.joanflo.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Time {

	
	public static String convertTimestampToString(Timestamp time) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(time);
	}
	
	
	
}
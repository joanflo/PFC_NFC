package com.joanflo.utils;

import android.annotation.SuppressLint;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

	
	@SuppressLint("SimpleDateFormat")
	public static String convertTimestampToString(Timestamp time) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(time);
	}

	
	@SuppressLint("SimpleDateFormat")
	public static Timestamp convertStringToTimestamp(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date parsedDate = null;
		try {
			parsedDate = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    return new java.sql.Timestamp(parsedDate.getTime());
	}
	
	
	
}
package com.joanflo.utils;

import android.annotation.SuppressLint;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple helper class to work with timestamps
 * @author Joanflo
 */
public class Time {

	
	@SuppressLint("SimpleDateFormat")
	/**
	 * Convert Timestamp object to a friendly String
	 * @param time
	 * @return
	 */
	public static String convertTimestampToString(Timestamp time) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(time);
	}
	
	

	@SuppressLint("SimpleDateFormat")
	/**
	 * Convert Date object to a friendly String
	 * @param time
	 * @return
	 */
	public static String convertDateToString(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(time);
	}

	
	
	@SuppressLint("SimpleDateFormat")
	/**
	 * Convert date String to Timestamp object
	 * @param time
	 * @return
	 */
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
	
	
	
	/**
	 * Difference between 2 timestamps in days
	 * @param initialDate
	 * @param finalDate
	 * @return
	 */
	public static int differenceInDays(Timestamp initialDate, Timestamp finalDate) {
		return (int) ((initialDate.getTime() - finalDate.getTime()) / (1000 * 60 * 60 * 24) );
	}
	
	
}
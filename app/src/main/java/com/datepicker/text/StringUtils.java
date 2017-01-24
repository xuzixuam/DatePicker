package com.datepicker.text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.TextUtils;

public class StringUtils {
	public static String formatDateNew(long millTime) {
		return formatDate(millTime, "yyyy-MM-dd");
	}

	public static String formatDate(long millTime, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date d = new Date(millTime);
		String time = sdf.format(d);
		return time;
	}
	
	public static long formatDateNew(String date,String format){
		if(TextUtils.isEmpty(format)) format = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date d = null;
		try {
			d = sdf.parse(date);
			return d.getTime();
		} catch (ParseException e) {
		}
		return 0;
	}
}

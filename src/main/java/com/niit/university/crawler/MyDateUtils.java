package com.niit.university.crawler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyDateUtils {

	public static String formatDate2(String pattern,Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.CHINA);
		return simpleDateFormat.format(date);
	}

}

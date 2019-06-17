package com.niit.university.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MyUtils {

	/**
	 * @category 将时间戳转换成字符串类型的格式
	 * @param timestamp
	 * @param pattern
	 * @return
	 */
	public static String formatDate(long timestamp, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.CHINA);
		return simpleDateFormat.format(timestamp);
	}
}

package com.tuxt.learn.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ai.frame.logger.Logger;
import com.ai.frame.logger.LoggerFactory;

public class DateUtil {
	private final static Logger logger = LoggerFactory.getUtilLog(DateUtil.class);
	/**
	 * 将日期按照特定格式转换成字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatString(Date date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	public static Date string2Date(String date,String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String getFormattedDate(java.sql.Timestamp dtDate,
			String strFormatTo) {
		if (dtDate == null) {
			return "";
		}
		if (dtDate.equals(new java.sql.Timestamp(0))) {
			return "";
		}
		strFormatTo = strFormatTo.replace('/', '-');
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
			if (Integer.parseInt(formatter.format(dtDate)) < 1900) {
				return "";
			} else {
				formatter = new SimpleDateFormat(strFormatTo);
				return formatter.format(dtDate);
			}
		} catch (Exception e) {
			logger.error("转换日期字符串格式时出错;",e.getMessage(),e);
			return "";
		}
	}
}

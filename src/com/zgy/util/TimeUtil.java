package com.zgy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	private static String TIME_DATE_TIME_STRING_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static String TIME_DATE_STRING_FORMAT = "yyyy-MM-dd";//

	public static long getCurrentTimeMillis() {
		return (System.currentTimeMillis() / 1000) * 1000;
	}

	public static long dateTimeStringToLong(String dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_TIME_STRING_FORMAT);
		Date dt2;
		try {
			dt2 = sdf.parse(dateTime);
			return dt2.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static String longToDateTimeString(long dateTimeMillis) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_TIME_STRING_FORMAT);
		Date dt = new Date(dateTimeMillis);
		return sdf.format(dt);
	}

	public static long dateStringToLong(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_STRING_FORMAT);
		Date dt2;
		try {
			dt2 = sdf.parse(date);
			return dt2.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static String longToDateString(long dateMillis) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_STRING_FORMAT);
		Date dt = new Date(dateMillis);
		return sdf.format(dt);
	}

	/**
	 * 将时间转为特定的格式 如: 1 转为 01
	 * 
	 * @param mmm
	 * @return
	 */
	public static String getformatString(int mmm) {
		if (mmm < 10) {
			if (mmm == 0) {
				return "00";
			} else {
				return "0" + mmm;
			}
		} else {
			return "" + mmm;
		}
	}
	
	/**
	 * 当前日期加n天后的日期，返回String (yyyy-mm-dd) 
	 * @Description:
	 * @param n
	 * @return
	 * @see: 
	 * @since: 
	 * @author: xuqq
	 * @date:2013-7-2
	 */
	public static String nDaysAfterNowDate(int n) { 
		Calendar rightNow = Calendar.getInstance(); 
		rightNow.add(Calendar.DAY_OF_MONTH,+n);
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_STRING_FORMAT);
		return sdf.format(rightNow.getTime()); 
	}
	
	/**
	 * 输入日期取星期几
	 * @Description:
	 * @param DateStr
	 * @return
	 * @see: 
	 * @since: 
	 * @author: xuqq
	 * @date:2013-7-4
	 */
	public static String getWeekDay(String DateStr){
	      SimpleDateFormat formatYMD=new SimpleDateFormat("yyyy-MM-dd");//formatYMD表示的是yyyy-MM-dd格式
	      SimpleDateFormat formatD=new SimpleDateFormat("E");//"E"表示"day in week"
	      Date d=null;
	      String weekDay="";
	      try{
	         d=formatYMD.parse(DateStr);//将String 转换为符合格式的日期
	         weekDay=formatD.format(d);
	      }catch (Exception e){
	         e.printStackTrace();
	      }
	     return weekDay;
	}
}

package com.c35.ptc.xutil;


import java.io.PrintWriter;
import java.io.StringWriter;

import android.util.Log;

/**
 * @author yangentao
 * 日志
 * Logcat日志级别: 0:禁用日志, 1:启用(所有日志),  2:verbose, 3: debug, 4:info, 5:warn, 6:error, 7:assert
 * 文件日志输出: 0:禁用日志, 1:启用(所有日志),  2:verbose, 3: debug, 4:info, 5:warn, 6:error, 7:assert
 */
public class XLog {

	private static  String TAG = "xlog";

	private static int LOGCAT_PRIORITY = 1;
	@SuppressWarnings("unused")
	private static int LOGFILE_PRIORITY = 0;
	
	public static void setTag(String tag) {
		TAG = tag;
	}
	/**
	 * 设置logcat的log级别, 见android.util.Log.VERBOSE等
	 * @param level 值是android.util.Log.VERBOSE/DEBUG/INFO/WARN等, 0表示全部禁用, 1表示全部启用
	 */
	public static void setLogLevel(int level) {
		LOGCAT_PRIORITY = level;
	}

	private static boolean enableLogcat(int level) {
		return LOGCAT_PRIORITY != 0 && level >= LOGCAT_PRIORITY;
	}

	@SuppressWarnings("unused")
	private static boolean enableLogfile(int level) {
//		return LOGFILE_PRIORITY != 0 && level >= LOGFILE_PRIORITY;
		return false;
	}

	public static void v(String msg) {
		println_native(Log.VERBOSE, TAG, msg);
	}

	public static void v(String msg, Throwable tr) {
		println_native(Log.VERBOSE, TAG, msg + '\n' + getStackTraceString(tr));
	}

	public static void v(String msg1, String msg2) {
		v(msg1 + ": " + msg2);
	}

	public static void d(String msg) {
		println_native(Log.DEBUG, TAG, msg);
	}

	public static void d(String msg, Throwable tr) {
		println_native(Log.DEBUG, TAG, msg + '\n' + getStackTraceString(tr));
	}
	public static void d(Throwable tr) {
		println_native(Log.DEBUG, TAG, getStackTraceString(tr));
	}

	public static void d(String msg1, String msg2) {
		d(msg1 + ": " + msg2);
	}

	public static void i(String msg) {
		println_native(Log.INFO, TAG, msg);
	}

	public static void i(String msg, Throwable tr) {
		println_native(Log.INFO, TAG, msg + '\n' + getStackTraceString(tr));
	}

	public static void i(String msg1, String msg2) {
		i(msg1 + ": " + msg2);
	}

	public static void w(String msg) {
		println_native(Log.WARN, TAG, msg);
	}

	public static void w(String msg, Throwable tr) {
		println_native(Log.WARN, TAG, msg + '\n' + getStackTraceString(tr));
	}

	public static void w(String msg1, String msg2) {
		w(msg1 + ": " + msg2);
	}

	public static void w(Throwable tr) {
		println_native(Log.WARN, TAG, getStackTraceString(tr));
	}

	public static void e(String msg) {
		println_native(Log.ERROR, TAG, msg);
	}

	public static void e(String msg, Throwable tr) {
		println_native(Log.ERROR, TAG, msg + '\n' + getStackTraceString(tr));
	}

	public static void e(Throwable tr) {
		e(TAG, tr);
	}

	public static void e(String msg1, String msg2) {
		e(msg1 + ": " + msg2);
	}

	private static String getStackTraceString(Throwable tr) {
		if (tr == null) {
			return "";
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		tr.printStackTrace(pw);
		return sw.toString();
	}

	private static void println_native(int priority, String tag, String msg) {
		if (enableLogcat(priority)) {
			Log.println(priority, tag, msg);
		}
//		if (enableLogfile(priority)) {
//			println_file(priority, tag, msg);
//		}
	}

//	private static void println_file(int priority, String tag, String msg) {
//		long threadID = Thread.currentThread().getId();
//		StringBuffer sb = new StringBuffer(256);
//		Calendar c = Util.getCurrentCalendar();
//		sb.append(Util.getDate(c, "-"));
//		sb.append(" ");
//		sb.append(Util.getTimeWithMillSec(c, ":"));
//		sb.append(" [");
//		sb.append(threadID);
//		sb.append("] [");
//		sb.append(priority);
//		sb.append("] [");
//		sb.append(tag);
//		sb.append("]  ");
//		sb.append(msg);
//		sb.append("\n");
//		putCache(sb.toString());
//
//	}

//	private static void putCache(String s) {
//		synchronized (cacheBuffer) {
//			cacheBuffer.append(s);
//			if (cacheBuffer.length() > 8192) {
//				FileOperator.write(getLogFileName(), cacheBuffer.toString());
//				cacheBuffer.setLength(0);
//			}
//		}
//	}

//	public static void flush() {
//		if (LOGFILE_PRIORITY != 0) {
//			if(cacheBuffer.length()==0) {
//				return;
//			}
//			synchronized (cacheBuffer) {
//				FileOperator.write(getLogFileName(), cacheBuffer.toString());
//				cacheBuffer.setLength(0);
//			}
//		}
//	}

//	private static StringBuffer cacheBuffer = new StringBuffer(8192 + 1024);

	private static String logFileName = null;

	public static String getLogFileName() {
//		if (logFileName == null) {
//			Calendar c = Util.getCurrentCalendar();
//			logFileName = "eq_" + Util.getDate(c, "-") + "_" + Util.getTime(c, "-") + ".log";
//		}
		return logFileName;
	}

}

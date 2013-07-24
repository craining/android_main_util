package com.c35.ptc.xutil;

public class StringUtil {

	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String bytes2HexString(byte[] data) {
		StringBuffer sb = new StringBuffer(32);
		for (byte b : data) {
			char low = DIGITS[b & 0x0F];
			char high = DIGITS[(b & 0xF0) >>> 4];
			sb.append(high);
			sb.append(low);
		}
		return sb.toString();
	}
	/**
	 * 字符串equal比较
	 * @Description:
	 * @param s1
	 * @param s2
	 * @return
	 * @see: 
	 * @since: 
	 * @author: huangyx2
	 * @date:2013-7-24
	 */
	public static boolean equal(String s1, String s2) {
		if (s1 == s2) {
			return true;
		}
		if (s1 == null) {
			return false;
		}
		return s1.equals(s2);
	}
	/**
	 * 
	 * @Description:
	 * @param str
	 * @return
	 * @see: 
	 * @since: 
	 * @author: huangyx2
	 * @date:2013-7-24
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * val 属于区间[from, to]
	 * 
	 * @Description:
	 * @param val
	 * @param from
	 * @param to
	 * @return
	 * @see:
	 * @since:
	 * @author: yangentao
	 * @date:2012-7-18
	 */

	public static boolean inRange11(int val, int from, int to) {
		return val >= from && val <= to;
	}

	/**
	 * val 属于区间[from, to)
	 * 
	 * @Description:
	 * @param val
	 * @param from
	 * @param to
	 * @return
	 * @see:
	 * @since:
	 * @author: yangentao
	 * @date:2012-7-18
	 */

	public static boolean inRange10(int val, int from, int to) {
		return val >= from && val < to;
	}

	/**
	 * 将a-z的小写字符转换成A-Z的大写字符, 如果给定的字符不在[a,z]区间内, 则返回原字符
	 * @Description:
	 * @param ch
	 * @return
	 * @see: 
	 * @since: 
	 * @author: yangentao
	 * @date:2012-7-18
	 */
	 
	public static char toUpper(char ch) {
		if (inRange11(ch, 'a', 'z')) {
			return (char) (ch - 'a' + 'A');
		}
		return ch;
	}
}

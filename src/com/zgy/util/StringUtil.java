package com.zgy.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 提取字符串中的数字
	 * 
	 * @param str
	 * @return
	 */
	public static String getNumbersFromString(String str) {
		String str1 = str.trim();
		String str2 = "";
		if (str1 != null && !"".equals(str1)) {
			for (int i = 0; i < str.length(); i++) {
				if (str1.charAt(i) >= 48 && str1.charAt(i) <= 57) {
					str2 += str1.charAt(i);
				}
			}
		}
		return str2;
	}

	/**
	 * 电话号码中除去多余的字符
	 * 
	 * @Description:
	 * @param number
	 * @return
	 */
	public static String getRidofSpeciallOfTel(String number) {
		String result = number;
		if (result != null) {
			result = result.replaceAll("-", "");
			result = result.replaceAll(" ", "");
			result = result.replaceAll("\\+86", "");//仅去掉大陆区号
			if (result.startsWith("12520")) {
				// 若是飞信短信
				result = result.replace("12520", "");
			}
		}

		return result;
	}

	/**
	 * 现在号码段分配如下：
	 * 
	 * 移动： 139 138 137 136 135 134 147 150 151 152 157 158 159 182 183 187 188
	 * 
	 * 联通： 130 131 132 155 156 185 186 145
	 * 
	 * 电信： 133 153 180 181 189
	 * 
	 * 参考：http://www.jihaoba.com/tools/?com=haoduan
	 * 
	 * @Description:
	 * @param number
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-4-16
	 */
	public static boolean isPhoneNumberFormat(String number) {

		Pattern p = Pattern.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(18[^4,\\D]))\\d{8}$");
		Matcher m = p.matcher(number);
		return m.matches();
	}
	
	/**
	 * 是否为空
	 * 
	 * @Description:
	 * @param str
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-7-18
	 */
	public static boolean isNull(String str) {
		if (str == null || str.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 过滤文件名中不合法字符
	 * 
	 * /:\*?"<>|
	 * 
	 * 注意java转义字符和正则
	 * 
	 * @Description:
	 * @param name
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-7-19
	 */
	public static String getRidofSpecialOfFileName(String name) {
		String result = name;
		if (result != null) {
			result = result.replaceAll("\\:", "");
			result = result.replaceAll("\\/", "");
			result = result.replaceAll("\\\\", "");
			result = result.replaceAll("\\*", "");
			result = result.replaceAll("\\?", "");
			result = result.replaceAll("<", "");
			result = result.replaceAll(">", "");
			result = result.replaceAll("\\|", "");
			result = result.replaceAll("\"", "");
			if (result.startsWith("12520")) {
				// 若是飞信短信
				result = result.replace("12520", "");
			}
		}
		return result;
	}
	
	/**
	 * 去掉字符串中的换行；用于邮件列表中预览的显示
	 * 
	 * @Description:
	 * @param str
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-9-25 注： \n 回车 \t 水平制表符 \s 空格 \r 换行
	 */
	public static String deleteEmptyLine(String str) {
		String dest = str;
		if (str != null) {
			Pattern p = Pattern.compile("\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * tab 换为一个空格，多个空格合并；用于邮件列表中预览的显示
	 * 
	 * @Description:
	 * @param str
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-9-25
	 */
	public static String combineBlank(String str) {
		String dest = str;
		if (str != null) {
			Pattern p = Pattern.compile("\t");
			Matcher m = p.matcher(str);
			dest = (m.replaceAll(" ")).trim().replaceAll(" +", " ");
		} else {
			dest = "";
		}
		return dest;
	}
}

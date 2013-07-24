package com.zgy.util;

import java.util.regex.Pattern;

/**
 * 
 * @Description:邮件富文本内容转为纯文本
 * @author:gongfacun
 * @see:
 * @since:
 * @copyright © 35.com
 * @Date:2012-11-2
 */
public class HtmlToText {

	// 多个空格过滤成一个 多个回车过滤成一个
	public static String HtmltoText(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		String newStr = "";

		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		// StringBuilder stringBuilder = null;
		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

			String regex = "[\n\r]+";

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			htmlStr = htmlStr.replaceAll("&nbsp;", "");
			htmlStr = htmlStr.replaceAll("&rsaquo;", "");
			htmlStr = htmlStr.replaceAll("&gt;", "");
			htmlStr = htmlStr.replaceAll("&lt;", "");
			htmlStr = htmlStr.replaceAll("&#", "");
			// htmlStr = getString(htmlStr);

			htmlStr = htmlStr.replaceAll(" {2,}", " ");
			htmlStr = htmlStr.replaceAll(regex, "\n");
		} catch (Exception e) {
			// ("Html2Text: " + e.getMessage());
			Debug.e("failfast", "failfast_AA", e);
		}
		return htmlStr;// 返回文本字符串
	}
}

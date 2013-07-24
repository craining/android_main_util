package com.zgy.util;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.zgy.mainutil.R;

/**
 * 
 * @Description:获取或打开多媒体文件
 * @author:gongfacun
 * @see:
 * @since:
 * @copyright © 35.com
 * @Date:2012-11-2
 */

public class OpenFileUtil {

	/**
	 * 获取文件后缀名
	 * 
	 * @param 文件对象
	 * @return 文件后缀名
	 */
	private static String getMIMEType(String fName) {
		String type = "";
		/* 取得扩展名 */
		String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();
		/* 按扩展名的类型决定MimeType */
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		end = "." + end;
		for (int i = 0; i < MIME_MapTable.length; i++) {
			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}

		/* 如果无法直接打开，就弹出软件列表给用户选择 */
		if (type != null && type.length() > 0) {
			type = "*/*";
		}
		return type;
	}

	/**
	 * 调用系统打开邮件附件
	 * 
	 * @param file
	 *            文件对象
	 */
	public static void fileHandle(String filePath, String fileName, Context context, String dialogTitle) {
		File file = new File(filePath);
		if (!file.exists()) {
			// TODO
			return;
		}
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		/* 调用getMIMEType()来取得MimeType */
		String type = getMIMEType(fileName);
		/* 设定intent的file与MimeType */
		intent.setDataAndType(Uri.fromFile(file), type);
		// intent.setDataAndType(Uri.fromFile(file),"*/*");
		context.startActivity(Intent.createChooser(intent, dialogTitle));
	}

	private static final String[][] MIME_MapTable = {
			// {后缀名， MIME类型}
	{ ".3gp", "video/3gpp" }, { ".apk", "application/vnd.android.package-archive" }, { ".asf", "video/x-ms-asf" }, { ".avi", "video/x-msvideo" }, { ".bin", "application/octet-stream" }, { ".bmp", "image/bmp" }, { ".c", "text/plain" }, { ".class", "application/octet-stream" }, { ".conf", "text/plain" }, { ".cpp", "text/plain" }, { ".doc", "application/msword" }, { ".docx", "application/msword" }, { ".exe", "application/octet-stream" }, { ".gif", "image/gif" }, { ".gtar", "application/x-gtar" }, { ".gz", "application/x-gzip" }, { ".h", "text/plain" }, { ".htm", "text/html" }, { ".html", "text/html" }, { ".jar", "application/java-archive" }, { ".java", "text/plain" }, { ".jpeg", "image/jpeg" }, { ".jpg", "image/jpeg" }, { ".js", "application/x-javascript" }, { ".log", "text/plain" }, { ".m3u", "audio/x-mpegurl" }, { ".m4a", "audio/mp4a-latm" }, { ".m4b", "audio/mp4a-latm" }, { ".m4p", "audio/mp4a-latm" }, { ".m4u", "video/vnd.mpegurl" }, { ".m4v", "video/x-m4v" }, { ".mov", "video/quicktime" }, { ".mp2", "audio/x-mpeg" }, { ".mp3", "audio/x-mpeg" }, { ".mp4", "video/mp4" }, { ".mpc", "application/vnd.mpohun.certificate" }, { ".mpe", "video/mpeg" }, { ".mpeg", "video/mpeg" }, { ".mpg", "video/mpeg" }, { ".mpg4", "video/mp4" }, { ".mpga", "audio/mpeg" }, { ".msg", "application/vnd.ms-outlook" }, { ".ogg", "audio/ogg" }, { ".pdf", "application/pdf" }, { ".png", "image/png" }, { ".pps", "application/vnd.ms-powerpoint" }, { ".ppt", "application/vnd.ms-powerpoint" }, { ".pptx", "application/vnd.ms-powerpoint" }, { ".prop", "text/plain" }, { ".rar", "application/x-rar-compressed" }, { ".rc", "text/plain" }, { ".rmvb", "audio/x-pn-realaudio" }, { ".rtf", "application/rtf" }, { ".sh", "text/plain" }, { ".tar", "application/x-tar" }, { ".tgz", "application/x-compressed" }, { ".txt", "text/plain" }, { ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" }, { ".wmv", "audio/x-ms-wmv" }, { ".wps", "application/vnd.ms-works" }, { ".xls", "application/vnd.ms-excel" }, { ".xlsx", "application/vnd.ms-excel" }, { ".xml", "text/plain" }, { ".z", "application/x-compress" }, { ".zip", "application/zip" }, { "", "*/*" } };
}

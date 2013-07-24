package com.zgy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.util.EncodingUtils;

import android.content.Context;

public class FileUtil {

	private static long size = 0;

	public FileUtil() {
		FileUtil.size = 0;
	}

	public static boolean delFile(File file) {
		if (file.isDirectory()) {
			return false;
		}

		return file.delete();
	}

	public static boolean delFileDir(File dir) {
		if (dir == null || !dir.exists() || dir.isFile()) {
			return false;
		}
		File[] listFiles = dir.listFiles();
		if (listFiles != null) {
			for (File file : listFiles) {
				if (file.isFile()) {
					file.delete();
				} else if (file.isDirectory()) {
					delFileDir(file);
				}
			}
		}
		// dir.delete();
		return true;
	}

	public long getFileSize(File dir) {

		try {
			if (!dir.isDirectory()) {
				setSize(getSize() + dir.length());
				// Debug.e("", dir.toString());
			} else {
				for (File file : dir.listFiles()) {
					if (!file.isDirectory()) {
						// Debug.e("", file.toString());
						setSize(getSize() + file.length());
					} else {
						getFileSize(file);// 閫掑綊
					}
				}
			}
		} catch (Exception e) {
			setSize(-1);
		}

		return getSize();
	}

	private static void setSize(long size) {
		FileUtil.size = size;
	}

	private static long getSize() {
		return FileUtil.size;
	}

	public static String sizeLongToString(long size) {
		if (size == 0) {
			return "0KB";
		} else {
			String a = "";
			if (size / 1024 < 1024.0) {
				a = String.format("%.2f", size / 1024.0) + "KB";
			} else if (size / 1048576 < 1024) {
				a = String.format("%.2f", size / 1048576.0) + "MB";
			} else {
				a = String.format("%.2f", size / 1073740824.0) + "GB";
			}
			return a;
		}
	}

	// 读文件方法
	public static String read(String fileName, Context context) {
		try {
			FileInputStream inputStream = context.openFileInput(fileName);
			byte[] b = new byte[inputStream.available()];
			inputStream.read(b);
			return new String(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	// 写文件
	public static void write(String content, String fileName, Context context) {
		try {
			FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			fos.write(content.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean writeFile(String str, File file, boolean add) {
		Debug.v("TT", file.toString() + "wrote in:" + str);

		FileOutputStream out;
		try {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}

			out = new FileOutputStream(file, add);
			String infoToWrite = str;
			out.write(infoToWrite.getBytes("utf-8"));
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static String getinfo(File file) {
		String str = "";
		FileInputStream in;
		try {
			in = new FileInputStream(file);
			int length = (int) file.length();
			byte[] temp = new byte[length];
			in.read(temp, 0, length);
			str = EncodingUtils.getString(temp, "utf-8");
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return str;
	}

	/**
	 * 拷贝目录下的所有文件到指定目录
	 * 
	 * @param srcDir
	 * @param destDir
	 * @return
	 * @throws IOException
	 */
	public static boolean copyFilesTo(File srcDir, File destDir) throws IOException {
		if (!destDir.exists())
			destDir.mkdirs();
		if (!srcDir.isDirectory() || !destDir.isDirectory()) {
			return false;// 判断是否是目录
		}
		File[] srcFiles = srcDir.listFiles();
		for (int i = 0; i < srcFiles.length; i++) {
			if (srcFiles[i].isFile()) {
				// 获得目标文件
				File destFile = new File(destDir.getPath() + "/" + srcFiles[i].getName());
				copyFileTo(srcFiles[i], destFile);
			} else if (srcFiles[i].isDirectory()) {
				File theDestDir = new File(destDir.getPath() + "/" + srcFiles[i].getName());
				copyFilesTo(srcFiles[i], theDestDir);
			}
		}

		return true;
	}

	/**
	 * 拷贝一个文件,srcFile源文件，destFile目标文件
	 * 
	 * @param srcFile
	 * @param destFile
	 * @return
	 * @throws IOException
	 */
	public static boolean copyFileTo(File srcFile, File destFile) throws IOException {
		if (srcFile.isDirectory() || destFile.isDirectory()) {
			return false;// 判断是否是文件
		}
		if (destFile.exists()) {
			destFile.delete();
		}
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(destFile);
		int readLen = 0;
		byte[] buf = new byte[1024];
		while ((readLen = fis.read(buf)) != -1) {
			fos.write(buf, 0, readLen);
		}
		fos.flush();
		fos.close();
		fis.close();

		return true;
	}

}

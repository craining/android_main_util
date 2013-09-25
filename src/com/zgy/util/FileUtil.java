package com.zgy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.apache.http.util.EncodingUtils;

import android.content.Context;

public class FileUtil {

	private static long size = 0;

	public FileUtil() {
		FileUtil.size = 0;
	}

	/**
	 * 删除非目录的文件
	 * 
	 * @Description:
	 * @param file
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-7-24
	 */
	public static boolean delFile(File file) {
		if (file.isDirectory()) {
			return false;
		}

		return file.delete();
	}

	/**
	 * 递归删除某目录及其所有子文件和子目录
	 * 
	 * @Description:
	 * @param dir
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-7-24
	 */
	public static boolean delFileDir(File dir) {
		if (dir == null || !dir.exists()) {
			return false;
		}
		if (dir.isFile()) {
			dir.delete();
		} else {
			File[] listFiles = dir.listFiles();
			if (listFiles == null || listFiles.length == 0) {
				dir.delete();
			} else {
				for (File file : listFiles) {
					if (file.isFile()) {
						file.delete();
					} else if (file.isDirectory()) {
						delFileDir(file);
					}
				}
			}
		}

		return true;
	}

	/**
	 * 递归获得文件目录大小
	 * 
	 * @Description:
	 * @param dir
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-7-24
	 */
	public long getFileSize(File dir) {

		try {
			if (!dir.isDirectory()) {
				setSize(getSize() + dir.length());
			} else {
				for (File file : dir.listFiles()) {
					if (!file.isDirectory()) {
						setSize(getSize() + file.length());
					} else {
						getFileSize(file);
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

	/**
	 * 转换文件的大小，将文件的字节数转换为kb、mb、或gb
	 * 
	 * @Description:
	 * @param size单位byte
	 * @return 保留1位小数
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-1-11
	 */
	public static String sizeLongToStringOne(long size) {
		String a = "";
		if (size < 1024) {
			a = String.format("%d byte(s)", size);
		} else if (size / 1024 < 1024.0) {
			a = String.format("%.1f KB", size / 1024.0);
		} else if (size / 1048576 < 1024) {
			a = String.format("%.1f MB", size / 1048576.0);
		} else {
			a = String.format("%.1f GB", size / 1073740824.0);
		}
		return a;
	}

	public static String sizeLongToStringTwo(long size) {
		long kb = 1024;
		long mb = (kb * 1024);
		long gb = (mb * 1024);
		if (size < kb) {
			return String.format("%d byte(s)", (int) size);
		} else if (size < mb) {
			return String.format("%.1f KB", size / kb);
		} else if (size < gb) {
			return String.format("%.1f MB", size / mb);
		} else {
			return String.format("%.1f GB", size / gb);
		}
	}

	public static String sizeLongToStringThree(long size) {
		if (size == 0) {
			return "0 KB";
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
	public static String androidReadFile(String fileName, Context context) {
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
	public static void androidWriteFile(String content, String fileName, Context context) {
		try {
			FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			fos.write(content.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean javaWriteFile(String str, File file, boolean add) {
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

	public static String javaReadFile(File file) {
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

	
	/**
	 * 复制文件
	 * 
	 * @Description:
	 * @param sourceFile
	 * @param targetFile
	 * @param replease
	 *            重名是否替换
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-9-25
	 */
	public static boolean copyOneFile(String sourceFile, String targetFile, boolean repleaseIfexists) {

		FileInputStream input = null;
		FileOutputStream output = null;

		File file = new File(targetFile);
		boolean existReplease = false;
		
		if (file.exists()) {
			if (repleaseIfexists) {
				existReplease = true;
				file = new File(targetFile + ".temp");
			} else {
				return false;
			}
		}
		try {
			input = new FileInputStream(sourceFile);
			output = new FileOutputStream(file);
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = input.read(b)) != -1) {
				output.write(b, 0, len);
			}
			output.flush();
			output.close();
			input.close();
			//同名替换
			if(existReplease) {
				new File(targetFile).delete();
				file.renameTo(new File(targetFile));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 从数组文件读取数组
	 * 
	 * @Description:
	 * @param file
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-10-10
	 */
	public static ArrayList<String> readArray(File file) {
		ArrayList<String> result = new ArrayList<String>();
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			result = (ArrayList<String>) in.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 把String，写入数组文件
	 * 
	 * @Description:
	 * @param email
	 * @param file
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-10-10
	 */
	public static void writeString2Array(String email, File file) {
		// 绑定账号时，先读取已经存储的绑定成功过的账号，若不存在当前绑定的账号，则添加到其中。
		ArrayList<String> a = new ArrayList<String>();
		if (file.exists()) {
			a = readArray(file);
		}
		if (a != null && !(a.contains(email))) {
			a.add(email);
			try {
				if (file.exists()) {
					file.delete();
					file.createNewFile();
				}
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
				out.writeObject(a);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

package com.zgy.util.aboutsu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Utils {
	/*
	 * isRoot 检查手机是否已经root权限
	 * 
	 * @author Hao LI
	 * 
	 * @since 1.0.0.0
	 * 
	 * @version 1.0.0.0
	 * 
	 * @return 返回是否root的布尔表示，是为true 否为false
	 */
	public static boolean isRoot() {
		// 检测是否root过
		DataInputStream stream;
		boolean flag = false;
		try {
			stream = terminal("ls /data");
			if (stream.readLine() != null) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	private static DataInputStream terminal(String command) throws Exception {
		Process process = Runtime.getRuntime().exec("su");
		// 执行到这，Superuser会跳出来，选择是否允许获取最高权限
		OutputStream outstream = process.getOutputStream();
		DataOutputStream DOPS = new DataOutputStream(outstream);
		InputStream instream = process.getInputStream();
		DataInputStream DIPS = new DataInputStream(instream);
		String temp = command + "\n";
		// 加回车
		DOPS.writeBytes(temp);
		// 执行
		DOPS.flush();
		// 刷新，确保都发送到outputstream
		DOPS.writeBytes("exit\n");
		// 退出
		DOPS.flush();
		process.waitFor();
		return DIPS;
	}

	public static boolean restore(String innerPath, String sdPath) {
		CommandRunner runner = CommandRunner.getInstance();
		File fInner = new File(innerPath);
		File fSd = new File(sdPath);
		try {

			runner.open();
			runner.runCommand("su");
			runner.runCommand("mount -t yaffs2 -o remount,rw /dev/block/mtdblock3 /system");
			runner.runCommand("cp -f " + sdPath + " " + innerPath);
			runner.runCommand("chmod 644 " + innerPath);

			if (android.os.Build.VERSION.SDK_INT >= 14) {
				File themeFont = new File("/data/system/theme/fonts/Roboto-Regular.ttf");
				if (themeFont.exists()) {
					runner.runCommand("cp -f " + sdPath + " /data/system/theme/fonts/Roboto-Regular.ttf");
					runner.runCommand("chmod 644 /data/system/theme/fonts/Roboto-Regular.ttf");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			runner.close();
		}
//		if (fInner.length() == fSd.length()) {
//			return true;
//		} else {
//			try {
//				runner.open();
//				runner.runCommand("su");
//				runner.runCommand("mount -t yaffs2 -o remount,rw /dev/block/mtdblock3 /system");
//
//				Log.e("", "after cp inner file length=" + fInner.length() + "   sd file length = " + fSd.length());
//				// cp失败，应该是系统没有cp命令，使用cat命令重新替换，（mv命令不允许将sd卡上的文件移到到内部存储上）
//				Log.e("", "cp 失败，用cat重新替换");
//				// runner.runCommand("chmod 777 " + innerPath);
//				runner.runCommand("cat " + sdPath + ">" + innerPath);
//
//				if (android.os.Build.VERSION.SDK_INT >= 14) {
//					File themeFont = new File("/data/system/theme/fonts/Roboto-Regular.ttf");
//					if (themeFont.exists()) {
//						// runner.runCommand("chmod 777 /data/system/theme/fonts/Roboto-Regular.ttf");
//						runner.runCommand("cat " + sdPath + ">/data/system/theme/fonts/Roboto-Regular.ttf");
//						runner.runCommand("chmod 644 /data/system/theme/fonts/Roboto-Regular.ttf");
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				runner.close();
//			}
//		}

		if (fInner.length() == fSd.length()) {
			return true;
		}

		return false;
	}

	/*
	 * reboot 重启手机
	 * 
	 * @author Hao LI
	 * 
	 * @since 1.0.0.0
	 * 
	 * @version 1.0.0.0
	 */
	public static void reboot() {
		OutputStream localOutputStream;
		try {
			localOutputStream = Runtime.getRuntime().exec("su").getOutputStream();
			DataOutputStream localDataOutputStream = new DataOutputStream(localOutputStream);
			localDataOutputStream.writeBytes("reboot\n");
			localDataOutputStream.writeBytes("exit\n");
			localDataOutputStream.flush();
			return;
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * @brief:复制单个文件
	 * @param: srcPath 源文件地址
	 * @param: destPath 目标文件地址
	 * @param: coverFalg 覆盖目标文件标志位
	 */
	public static void copyFile(String srcPath, String destPath, boolean coverFalg)
	{
		try
		{
			File srcFile = new File(srcPath);
			File destFile = new File(destPath);
			if (srcFile.isDirectory() || destFile.isDirectory())
			{
				return;
			}
			if (!destFile.getParentFile().exists())
			{
				destFile.mkdir();
			}
			if (srcFile.exists() && (!destFile.exists() || coverFalg))
			{
				FileInputStream inStream = new FileInputStream(srcPath);
				FileOutputStream outStream = new FileOutputStream(destPath);
				byte[] buffer = new byte[4096];
				int length = 0;
				while ((length = inStream.read(buffer)) != -1)
				{
					outStream.write(buffer, 0, length);
				}
				inStream.close();
				outStream.close();
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

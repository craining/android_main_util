package com.zgy.util.aboutsu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import android.util.Log;

public class CommandRunner {

	private static final String TAG = "CommandRunner";
	/**
	 * 执行命令
	 */
	private static CommandRunner mRunner;

	private boolean isRunning = false;
	// private OutputStream localOutputStream;
	// private InputStream localInputStream;
	private Process localProcess;

	// 转成DataOutputStream方便写入字符串
	DataOutputStream localDataOutputStream;
	DataInputStream localDataInputStream;

	private CommandRunner() {

	}

	public static CommandRunner getInstance() {
		if (mRunner == null) {
			mRunner = new CommandRunner();

		}
		return mRunner;
	}

	public void open() throws Exception {
		if (isRunning) {
			throw new Exception("the runner before is not close!");
		} else {
			isRunning = true;
		}
	}

	public void close() {
		isRunning = false;
		try {
			localDataOutputStream.writeBytes("exit\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			localDataOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			localDataInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		localDataOutputStream = null;
		localDataInputStream = null;

		try {
			localProcess.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		localProcess = null;
	}

	public void runCommand(String command) throws Exception {
		if (!isRunning) {
			throw new Exception("runner is not open!");
		}
		
		if(localProcess == null) {
			localProcess = Runtime.getRuntime().exec(command);
			localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
			localDataInputStream = new DataInputStream(localProcess.getInputStream());
		}

		Log.v(TAG, "run >" + command);

		byte[] data = command.concat("\n").getBytes();

		localDataOutputStream.write(data);
		localDataOutputStream.flush();
		
//		boolean read = false;
//		if(read) {
//			String result = readLine();
//			if(result.length() <= 0) {
//				throw new Exception("response is null");
//			}  else {
//				Log.v(TAG, "Response<  " + result);
//			}
//		}
		
	}

	private String readLine() throws IOException {
		StringBuilder sb = new StringBuilder();
		int d;
		while ((d = localDataInputStream.read()) != -1) {
			sb.append((char) d);
		}
		return sb.toString();
	}

}
 

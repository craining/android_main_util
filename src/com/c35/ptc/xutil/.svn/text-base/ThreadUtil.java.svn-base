package com.c35.ptc.xutil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import android.os.Handler;
import android.os.Looper;

public class ThreadUtil {
	
	private static Handler uiHandler = null;
	private static ScheduledExecutorService pool = null;
	private static ScheduledExecutorService poolHigh = null;
	
	private static void checkInited() {
		if(uiHandler == null) {
			throw new IllegalStateException("ThreadUtil.init NOT inited, you must call ThreadUtil.init first");
		}
	}

	public static Handler getUIHandler() {
		checkInited();
		return uiHandler;
	}

	public static ScheduledExecutorService getPool() {
		checkInited();
		return pool;
	}

	public static ScheduledExecutorService getPoolHigh() {
		checkInited();
		return poolHigh;
	}

	public static synchronized void shutdown() {
		uiHandler = null;
		if (pool != null) {
			pool.shutdown();
		}
		if (poolHigh != null) {
			poolHigh.shutdown();
		}
	}

	public static synchronized void shutdownNow() {
		uiHandler = null;
		if (pool != null) {
			pool.shutdownNow();
		}
		if (poolHigh != null) {
			poolHigh.shutdownNow();
		}
	}

	/**
	 * 初始化, 必须在主线程调用
	 */
	public static synchronized void init(int poolSize, int highPoolSize) {
		if (uiHandler != null) {
			throw new IllegalStateException("ThreadUtil.init already inited");
		}
		uiHandler = new Handler(Looper.getMainLooper());
		if (poolSize > 0) {
			pool = Executors.newScheduledThreadPool(poolSize, new ThreadFactory() {

				@Override
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r);
					t.setPriority(Thread.MIN_PRIORITY);
					t.setDaemon(true);
					return t;
				}
			});
		}
		if (highPoolSize > 0) {
			poolHigh = Executors.newScheduledThreadPool(highPoolSize, new ThreadFactory() {

				@Override
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r);
					t.setPriority(Thread.NORM_PRIORITY - 1);
					t.setDaemon(true);
					return t;
				}
			});
		}
	}

}

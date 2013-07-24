package com.c35.ptc.xutil.task;

import java.util.concurrent.ExecutorService;

import android.util.Log;

import com.c35.ptc.xutil.ThreadUtil;

/**
 * background-foreground-task<br/>
 * 按照指定顺序执行后台任务和主线程任务的类<br/>
 * 类似AsyncTask
 * 
 * @author yangentao
 * 
 */
public abstract class ThreadTask {
	public static final int BACK_FORE = 0;// 先在后台线程中执行onBack, 再在主线程中执行onFore
	public static final int FORE_BACK = 1;// 先在主线程中执行onFore, 再在后台线程中执行onBack
	public static final int BACK = 2;// 只在后台线程中执行onBack
	public static final int FORE = 3;// 只在主线程中执行onFore

	private boolean highPriority = false;

	public ThreadTask(boolean autoStart) {
		this(autoStart, false);
	}

	/**
	 * @param autoStart
	 *            是否自动开始, 构造函数开始, 不用调用strt方法
	 * @param highPriority
	 *            是否高优先级
	 */
	public ThreadTask(boolean autoStart, boolean highPriority) {
		this.highPriority = highPriority;
		if (autoStart) {
			start();
		}
	}

	private void callFore(final boolean isHight, final boolean callBack) {
		ThreadUtil.getUIHandler().post(new Runnable() {
			@Override
			public void run() {
				try {
					onFore();
				}catch (Exception e) {
					Log.e("ThreadTask", "onFore", e);
				}
				if (callBack) {
					callBack(isHight, false);
				}
			}
		});
	}

	private void callBack(final boolean isHigh, final boolean callFore) {
		ExecutorService es;
		if (isHigh) {
			es = ThreadUtil.getPoolHigh();
		} else {
			es = ThreadUtil.getPool();
		}

		es.submit(new Runnable() {
			@Override
			public void run() {
				try {
					onBack();
				}catch (Exception e) {
					Log.e("ThreadTask", "onFore", e);
				}
				if (callFore) {
					callFore(isHigh, false);
				}
			}
		});
	}

	private boolean started = false;

	/**
	 * @param isHigh
	 *            是否高优先级
	 */
	public void start() {
		if (started) {
			return;
		}
		started = true;
		int order = getOrder();
		if (order == FORE || order == FORE_BACK) {
			callFore(highPriority, order == FORE_BACK);
			return;
		}
		if (order == BACK || order == BACK_FORE) {
			callBack(highPriority, order == BACK_FORE);
		}
	}

	/**
	 * 调用顺序
	 * 
	 * @return
	 */
	protected abstract int getOrder();

	/**
	 * 在后台线程执行的任务
	 * 
	 * @param valuesMap
	 */
	public abstract void onBack();

	/**
	 * 在主线程执行的任务
	 * 
	 * @param valuesMap
	 */
	public abstract void onFore();

}

package com.c35.ptc.xutil;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


/**
 * 如果有任务, 则间断执行设定回调; 没有新任务时不执行
 * @author yangentao
 *
 */
public class IntervalTask {
	private static final int DEFAULT_DELAY = 3;
	private int seconds;
	private Runnable callback;
	private ScheduledFuture<?> refreshVCardTask = null;

	public IntervalTask(Runnable callback) {
		this(DEFAULT_DELAY, callback);
	}

	public IntervalTask(int seconds, Runnable callback) {
		this.seconds = seconds;
		this.callback = callback;
	}

	public synchronized void addTask() {
		if (refreshVCardTask == null) {
			refreshVCardTask = ThreadUtil.getPoolHigh().schedule(new Runnable() {
				@Override
				public void run() {
					callback.run();
					synchronized (IntervalTask.this) {
						refreshVCardTask = null;
					}
				}
			}, seconds, TimeUnit.SECONDS);
		}
	}

}

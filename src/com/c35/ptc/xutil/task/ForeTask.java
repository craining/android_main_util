package com.c35.ptc.xutil.task;

/**
 * 前台线程任务
 * @Description:
 * @author:huangyx2  
 * @see:   
 * @since:      
 * @copyright © 35.com
 * @Date:2013-7-24
 */
public abstract class ForeTask extends ThreadTask {
	public ForeTask(boolean autoStart) {
		super(autoStart);
	}

	@Override
	protected final int getOrder() {
		return FORE;
	}

	/**
	 * 在后台线程执行的任务
	 * 
	 * @param valuesMap
	 */
	@Override
	public final void onBack() {

	}

	/**
	 * 在主线程执行的任务
	 * 
	 * @param valuesMap
	 */
	@Override
	public abstract void onFore();
}

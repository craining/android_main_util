package com.c35.ptc.xutil.task;

/**
 * 前后台线程切换，先前台再后台
 * @Description:
 * @author:huangyx2  
 * @see:   
 * @since:      
 * @copyright © 35.com
 * @Date:2013-7-24
 */
public abstract class ForeBackTask extends ThreadTask {
	public ForeBackTask(boolean autoStart) {
		super(autoStart);
	}

	/**
	 * @param autoStart
	 *            是否自动开始, 构造函数开始, 不用调用strt方法
	 * @param highPriority
	 *            是否高优先级
	 */
	public ForeBackTask(boolean autoStart, boolean highPriority) {
		super(autoStart, highPriority);
	}

	@Override
	protected final int getOrder() {
		return FORE_BACK;
	}

	/**
	 * 在主线程执行的任务
	 * 
	 * @param valuesMap
	 */
	@Override
	public abstract void onFore();

	/**
	 * 在后台线程执行的任务
	 * 
	 * @param valuesMap
	 */
	@Override
	public abstract void onBack();
}

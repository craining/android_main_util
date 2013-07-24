package com.zgy.util;

import java.util.List;

import android.app.ActivityManager;
import android.content.Context;


public class ServiceUtil {
	/**
	 * ͨ��Service���������ж��Ƿ�����ĳ������
	 * 
	 * @param mServiceList
	 * @param className
	 * @return
	 */
	public static boolean isServiceStarted(Context context, String serviceName) {

		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> mServiceList = activityManager.getRunningServices(100);

		for (int i = 0; i < mServiceList.size(); i++) {
			if (serviceName.equals(mServiceList.get(i).service.getClassName())) {
				return true;
			}
		}
		return false;
	}
}

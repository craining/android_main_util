package com.zgy.util;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class NetworkUtil {

	public static boolean isWifiEnabled(Context context) {
		ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		if (wifi == State.CONNECTING || wifi == State.CONNECTED) {
			return true;
		}
		return false;
	}

	public static boolean isMobileEnabled(Context context) {
		ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		if (wifi != State.CONNECTED) {
			State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
			if (mobile == State.CONNECTED) {
				return true;
			}
		}
		return false;

	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connect = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connect == null) {
			return false;
		} else {
			NetworkInfo[] info = connect.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 开关Gprs
	 * 
	 * @Description:
	 * @param context
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-7-19
	 */
	public static void setMobileNetEnable(Context context, boolean open) {

		if (NetworkUtil.isNetworkAvailable(context)) {
			Debug.e("", " no need to turn mobile net work");
			return;
		}
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		try {
			invokeBooleanArgMethod(mConnectivityManager, "setMobileDataEnabled", open);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	private static Object invokeBooleanArgMethod(ConnectivityManager mConnectivityManager, String methodName, boolean value) throws Exception {
		Class ownerClass = mConnectivityManager.getClass();
		Class[] argsClass = new Class[1];
		argsClass[0] = boolean.class;
		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(mConnectivityManager, value);
	}
	
	
	
	/**
	 * 获得ip地址
	 * 
	 * @Description:
	 * @param context
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-4-10
	 */
	public static String getIp(Context context) {
		String ip = "10.0.0.2";
		if (isNetworkAvailable(context)) {
			if (isWifiEnabled(context)) {
				WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
				int ipAddress = wifiInfo.getIpAddress();
				ip = (ipAddress & 0xFF) + "." + ((ipAddress >> 8) & 0xFF) + "." + ((ipAddress >> 16) & 0xFF) + "." + (ipAddress >> 24 & 0xFF);
			} else if (isMobileEnabled(context)) {
				ip = getLocalIpAddress();
			}
		}
		Debug.v("getIp", "IP=" + ip);
		return ip;
	}

	private static String getLocalIpAddress() {
		try {

			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Debug.e("WifiPreference IpAddress", ex.toString());
		}

		return null;

	}
}

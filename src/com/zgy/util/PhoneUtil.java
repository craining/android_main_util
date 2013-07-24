package com.zgy.util;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class PhoneUtil {

	private static final String TAG = "PhoneUtil";
	
	/**
	 * 获得手机信息
	 * 
	 * @Description:
	 * @param con
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-12-3
	 */
	public static String getHandsetInfo(Context con) {

		TelephonyManager tm = (TelephonyManager) con.getSystemService(Context.TELEPHONY_SERVICE);
		StringBuilder sb = new StringBuilder();

		int version = -1;
		String manufacturer = "null";
		String model = "null";
		String device = "null";

		try {
			Class<android.os.Build.VERSION> build_version_class = android.os.Build.VERSION.class;
			// 取得 android 版本
			java.lang.reflect.Field field;
			field = build_version_class.getField("SDK_INT");
			version = (Integer) field.get(new android.os.Build.VERSION());
			sb.append("\r\nSDK_INT = " + version);

			Class<android.os.Build> build_class = android.os.Build.class;
			// 取得牌子
			java.lang.reflect.Field manu_field = build_class.getField("MANUFACTURER");
			manufacturer = (String) manu_field.get(new android.os.Build());
			sb.append("\r\nManufacturer = " + manufacturer);
			// 取得型號
			java.lang.reflect.Field field2 = build_class.getField("MODEL");
			model = (String) field2.get(new android.os.Build());
			sb.append("\r\nMODEL = " + model);
			// 模組號碼
			java.lang.reflect.Field device_field = build_class.getField("DEVICE");
			device = (String) device_field.get(new android.os.Build());
			sb.append("\r\nDEVICE = " + device);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		sb.append("\r\nDeviceId(IMEI) = " + tm.getDeviceId());
		// sb.append("\r\nDeviceSoftwareVersion = " +
		// tm.getDeviceSoftwareVersion());
		// sb.append("\r\nLine1Number = " + tm.getLine1Number());
		// sb.append("\r\nNetworkCountryIso = " + tm.getNetworkCountryIso());
		// sb.append("\r\nNetworkOperator = " + tm.getNetworkOperator());
		// sb.append("\r\nNetworkOperatorName = " +
		// tm.getNetworkOperatorName());
		// sb.append("\r\nNetworkType = " + tm.getNetworkType());
		// sb.append("\r\nPhoneType = " + tm.getPhoneType());
		sb.append("\r\nSimCountryIso = " + tm.getSimCountryIso());
		// sb.append("\r\nSimOperator = " + tm.getSimOperator());
		sb.append("\r\nSimOperatorName = " + tm.getSimOperatorName());
		// sb.append("\r\nSimSerialNumber = " + tm.getSimSerialNumber());
		// sb.append("\r\nSimState = " + tm.getSimState());
		sb.append("\r\nSubscriberId(IMSI) = " + tm.getSubscriberId());
		// sb.append("\r\nVoiceMailNumber = " + tm.getVoiceMailNumber());

		Debug.i("info", sb.toString());
		return sb.toString();
	}

	public static void hideKeyboard(Context con, EditText edit) {
		// 隐藏软件盘
		InputMethodManager imm = (InputMethodManager) con.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
	}

	/**
	 * 判断存储卡是否存在
	 * 
	 * @return
	 */
	public static boolean existSDcard() {
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {
			return true;
		} else
			return false;
	}

	/**
	 * 控制飞行模式开关
	 * 
	 * @param con
	 * @param on
	 */
	public static void setAirplaneModeOff(Context con, boolean off) {
		// Change the system setting
		Settings.System.putInt(con.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, off ? 1 : 0);
		// Post the intent
		Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
		intent.putExtra("state", off);
		con.sendBroadcast(intent);
	}

	/**
	 * 发送短信
	 * 
	 * @param con
	 * @param number
	 * @param content
	 */
	public static void sendMessage(Context con, String tonumber, String content) {
		// 直接调用短信接口发短信
		SmsManager smsManager = SmsManager.getDefault();
		List<String> divideContents = smsManager.divideMessage(content);
		for (String text : divideContents) {
			smsManager.sendTextMessage(tonumber, null, text, null, null);
			Debug.e(TAG, "send msg, to:" + tonumber + "  content: " + content);
		}
	}

	
	/**
	 * 获得手机的IMEI
	 * 
	 * @Description:
	 * @param con
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-3-6
	 */
	public static String getPhoneImei(Context con) {
		TelephonyManager tm = (TelephonyManager) con.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	/**
	 * 获得手机号
	 * 
	 * @Description:
	 * @param con
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-3-6
	 */
	public static String getPhoneSimNumber(Context con) {
		TelephonyManager tm = (TelephonyManager) con.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getLine1Number();
	}

	/**
	 * 获得手机的IMEI
	 * 
	 * @Description:
	 * @param con
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-3-6
	 */
	public static String getPhoneImsi(Context con) {
		TelephonyManager tm = (TelephonyManager) con.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getSubscriberId();
	}
}

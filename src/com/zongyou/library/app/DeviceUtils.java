package com.zongyou.library.app;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * @author Altas
 * @email Altas.Tutu@gmail.com
 * @time 2015-2-11 下午11:10:28
 */
public class DeviceUtils {
	public static final String TAG = DeviceUtils.class.getSimpleName();

	/**
	 * 获取底部 navigation bar 高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getNavigationBarHeight(Context context) {
		Resources resources = context.getResources();
		int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
		int height = resources.getDimensionPixelSize(resourceId);
		return height;
	}
	/**
	 * 获取顶部status bar 高度
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Resources resources = context.getResources();
		int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
		int height = resources.getDimensionPixelSize(resourceId);
		Log.v("dbw", "Status height:" + height);
		return height;
	}
	/**
	 * 获取屏幕真实高度，减去底部navigation bar高度
	 * @param context
	 * @return
	 */
	public static int getWindowRealHeight(Activity context){
		return context.getWindow().getWindowManager().getDefaultDisplay().getHeight()-getNavigationBarHeight(context);
	}
	/**
	 * 隐藏Navigation Bar
	 * @param context
	 */
	public static void hideNavigationBar(Activity context){
	if (android.os.Build.VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH) {
		context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

	}
	}
	/**
	 * 是否含有虚拟按键
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasPermanentMenuKey(Context context) {
		if (VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH) {
			return ViewConfiguration.get(context).hasPermanentMenuKey();
		}
		boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
		boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);

		return !(hasBackKey && hasHomeKey);
	}

	/**
	 * 获取设备信息
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceInfo(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		StringBuilder sb = new StringBuilder();
		sb.append("\nDeviceId(IMEI) = " + tm.getDeviceId());
		sb.append("\nDeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion());
		sb.append("\nLine1Number = " + tm.getLine1Number());
		sb.append("\nNetworkCountryIso = " + tm.getNetworkCountryIso());
		sb.append("\nNetworkOperator = " + tm.getNetworkOperator());
		sb.append("\nNetworkOperatorName = " + tm.getNetworkOperatorName());
		sb.append("\nNetworkType = " + tm.getNetworkType());
		sb.append("\nPhoneType = " + tm.getPhoneType());
		sb.append("\nSimCountryIso = " + tm.getSimCountryIso());
		sb.append("\nSimOperator = " + tm.getSimOperator());
		sb.append("\nSimOperatorName = " + tm.getSimOperatorName());
		sb.append("\nSimSerialNumber = " + tm.getSimSerialNumber());
		sb.append("\nSimState = " + tm.getSimState());
		sb.append("\nSubscriberId(IMSI) = " + tm.getSubscriberId());
		sb.append("\nVoiceMailNumber = " + tm.getVoiceMailNumber());
		return sb.toString();
	}

	/**
	 * 获取DeviceId
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceId(Context context) {
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			return telephonyManager.getDeviceId();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static PackageInfo getPackageInfo(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			return null;
		}
	}
}

package com.zongyou.library.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 判断网络状态工具
 * 
 * @author Altas
 * @date 2014年9月2日
 */
public final class NetworkUtils {
	public static final String LOG_TAG = NetworkUtils.class.getSimpleName();

	/**
	 * 网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvaiable(Context context) {
		if (context == null) {
			throw new NullPointerException("context not be null");
		}
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService("connectivity");
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		return (info != null) && (info.isConnected());
	}

	/**
	 * WIFI是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiActive(Context context) {
		if (context == null) {
			throw new NullPointerException("context not be null");
		}
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService("connectivity");
		NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if ((info[i].getTypeName().equals("WIFI"))
						&& (info[i].isConnected()))
					return true;
			}
		}
		return false;
	}
}
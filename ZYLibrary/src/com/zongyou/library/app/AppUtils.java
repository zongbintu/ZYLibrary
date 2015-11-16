package com.zongyou.library.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * APP相关工具类
 * 
 * @author Altas
 * @email Altas.TuTu@gmail.com
 * @date 2014年9月27日
 */
public class AppUtils {
	/**
	 * 隐藏输入法
	 * 
	 * @param activity
	 */
	public static void hideSoftInput(Activity activity) {
		if (activity != null) {
			InputMethodManager inputMethodManager = (InputMethodManager) activity
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			View view = activity.getWindow().getDecorView();
			if (view != null) {
				inputMethodManager.hideSoftInputFromWindow(
						view.getWindowToken(), 0);
			}
		}
	}

	/**
	 * 跳转至HOME
	 * 
	 * @param context
	 */
	public static void startHome(Context context) {
		// 跳转至home
		Intent homeIntent = new Intent(Intent.ACTION_MAIN);

		homeIntent.addCategory(Intent.CATEGORY_HOME);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		context.startActivity(homeIntent);
	}

}

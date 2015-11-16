package com.zongyou.library.util.log;

import android.util.Log;
/**
 * 日志工具类
 * @author Altas
 * @date 2014年9月23日
 */
public final class CustomLogger implements Logger {
	private static CustomLogger mInstance;

	public static CustomLogger getInstance() {
		return mInstance == null ? (CustomLogger.mInstance = new CustomLogger())
				: mInstance;
	}

	public void v(String tag, String msg) {
		Log.v(tag, msg);
	}

	public void v(String tag, String msg, Throwable tr) {
		Log.v(tag, msg, tr);
	}

	public void v(String tag, String format, Object[] args) {
		Log.v(tag, String.format(format, args));
	}

	public void v(String tag, Throwable tr, String format, Object[] args) {
		Log.v(tag, String.format(format, args), tr);
	}

	public void d(String tag, String msg) {
		Log.d(tag, msg);
	}

	public void d(String tag, String msg, Throwable tr) {
		Log.d(tag, msg, tr);
	}

	public void d(String tag, String format, Object[] args) {
		Log.d(tag, String.format(format, args));
	}

	public void d(String tag, Throwable tr, String format, Object[] args) {
		Log.d(tag, String.format(format, args), tr);
	}

	public void i(String tag, String msg) {
		Log.i(tag, msg);
	}

	public void i(String tag, String msg, Throwable tr) {
		Log.i(tag, msg, tr);
	}

	public void i(String tag, String format, Object[] args) {
		Log.i(tag, String.format(format, args));
	}

	public void i(String tag, Throwable tr, String format, Object[] args) {
		Log.i(tag, String.format(format, args), tr);
	}

	public void w(String tag, String msg) {
		Log.w(tag, msg);
	}

	public void w(String tag, String msg, Throwable tr) {
		Log.w(tag, msg);
	}

	public void w(String tag, Throwable tr) {
		Log.w(tag, tr);
	}

	public void w(String tag, String format, Object[] args) {
		Log.w(tag, String.format(format, args));
	}

	public void w(String tag, Throwable tr, String format, Object[] args) {
		Log.w(tag, String.format(format, args), tr);
	}

	public void e(String tag, String msg) {
		Log.e(tag, msg);
	}

	public void e(String tag, String msg, Throwable tr) {
		Log.e(tag, msg, tr);
	}

	public void e(String tag, String format, Object[] args) {
		Log.e(tag, String.format(format, args));
	}

	public void e(String tag, Throwable tr, String format, Object[] args) {
		Log.e(tag, String.format(format, args), tr);
	}

	public void wtf(String tag, String msg) {
		Log.wtf(tag, msg);
	}

	public void wtf(String tag, String msg, Throwable tr) {
		Log.wtf(tag, msg, tr);
	}

	public void wtf(String tag, Throwable tr) {
		Log.wtf(tag, tr);
	}

	public void wtf(String tag, String format, Object[] args) {
		Log.wtf(tag, String.format(format, args));
	}

	public void wtf(String tag, Throwable tr, String format, Object[] args) {
		Log.wtf(tag, String.format(format, args), tr);
	}

	public void trace(int level, String tag, String fileName, String msg) {
		switch (level) {
		case 2:
			Log.v(tag, msg);
			break;
		case 3:
			Log.d(tag, msg);
			break;
		case 4:
			Log.i(tag, msg);
			break;
		case 5:
			Log.w(tag, msg);
			break;
		case 6:
			Log.e(tag, msg);
		}
	}
}
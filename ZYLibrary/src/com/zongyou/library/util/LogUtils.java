package com.zongyou.library.util;

import com.zongyou.library.BuildConfig;
import com.zongyou.library.util.log.CustomLogger;
import com.zongyou.library.util.log.Logger;
import com.zongyou.library.util.log.OtherUtils;

/**
 * 日志工具类
 * 
 * @author Altas
 * @date 2014年9月23日
 */
public final class LogUtils {
	public static int LEVEL = 3;
	public static Logger mLogger = CustomLogger.getInstance();
	public static String customTagPrefix = "";

	private static boolean allowPrint(int level) {
		return level >= LEVEL;
	}

	private static String generateTag() {
		String tag = "%s.%s(L:%d)";
		StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
		String callerClazzName = caller.getClassName();
		callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
		tag = String.format(tag, new Object[] { callerClazzName, caller.getMethodName(), Integer.valueOf(caller.getLineNumber()) });
		tag = customTagPrefix + ":" + tag;
		return tag;
	}

	public static void trace(int level, String tag, String fileName, String msg) {
		if (!allowPrint(level)) {
			return;
		}
		mLogger.trace(level, tag, fileName, msg);
	}

	public static void v(String tag, String msg) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.v(tag, msg);
	}

	public static void v(String tag, String msg, Throwable tr) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.v(tag, msg, tr);
	}

	public static void v(String tag, String format, Object[] args) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.v(tag, String.format(format, args));
	}

	public static void v(String tag, Throwable tr, String format, Object[] args) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.v(tag, String.format(format, args), tr);
	}

	public static void v(String msg) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.v(generateTag(), msg);
	}

	public static void v(String msg, Throwable tr) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.v(generateTag(), msg, tr);
	}

	public static void d(String tag, String msg) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.d(tag, msg);
	}

	public static void d(String tag, String msg, Throwable tr) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.d(tag, msg, tr);
	}

	public static void d(String tag, String format, Object[] args) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.d(tag, String.format(format, args));
	}

	public static void d(String tag, Throwable tr, String format, Object[] args) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.d(tag, String.format(format, args), tr);
	}

	public static void d(String msg) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.d(generateTag(), msg);
	}

	public static void d(String msg, Throwable tr) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.d(generateTag(), msg, tr);
	}

	public static void i(String tag, String msg) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.i(tag, msg);
	}

	public static void i(String tag, String msg, Throwable tr) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.i(tag, msg, tr);
	}

	public static void i(String tag, String format, Object[] args) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.i(tag, String.format(format, args));
	}

	public static void i(String tag, Throwable tr, String format, Object[] args) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.i(tag, String.format(format, args), tr);
	}

	public static void i(String msg) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.i(generateTag(), msg);
	}

	public static void i(String msg, Throwable tr) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.i(generateTag(), msg, tr);
	}

	public static void w(String tag, String msg) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.w(tag, msg);
	}

	public static void w(String tag, String msg, Throwable tr) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.w(tag, msg, tr);
	}

	public static void w(String tag, String format, Object[] args) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.w(tag, String.format(format, args));
	}

	public static void w(String tag, Throwable tr, String format, Object[] args) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.w(tag, String.format(format, args), tr);
	}

	public static void w(String msg) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.w(generateTag(), msg);
	}

	public static void w(Throwable tr) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.w(generateTag(), tr);
	}

	public static void w(String msg, Throwable tr) {
		if (!BuildConfig.DEBUG) {
			return;
		}
		mLogger.w(generateTag(), msg, tr);
	}

	public static void e(String tag, String msg) {
		mLogger.e(tag, msg);
	}

	public static void e(String tag, String msg, Throwable tr) {
		mLogger.e(tag, msg, tr);
	}

	public static void e(String tag, String format, Object[] args) {
		mLogger.e(tag, String.format(format, args));
	}

	public static void e(String tag, Throwable tr, String format, Object[] args) {
		mLogger.e(tag, String.format(format, args), tr);
	}

	public static void e(String msg) {
		mLogger.e(generateTag(), msg);
	}

	public static void e(String msg, Throwable tr) {
		mLogger.e(generateTag(), msg, tr);
	}

	public static void wtf(String tag, String msg) {
		if (!allowPrint(7)) {
			return;
		}
		mLogger.wtf(tag, msg);
	}

	public static void wtf(String tag, String msg, Throwable tr) {
		if (!allowPrint(7)) {
			return;
		}
		mLogger.wtf(tag, msg, tr);
	}

	public static void wtf(String tag, String format, Object[] args) {
		if (!allowPrint(7)) {
			return;
		}
		mLogger.wtf(tag, String.format(format, args));
	}

	public static void wtf(String tag, Throwable tr, String format, Object[] args) {
		if (!allowPrint(7)) {
			return;
		}
		mLogger.wtf(tag, String.format(format, args), tr);
	}

	public static void wtf(String msg) {
		if (!allowPrint(7)) {
			return;
		}
		mLogger.wtf(generateTag(), msg);
	}

	public static void wtf(String msg, Throwable tr) {
		if (!allowPrint(7)) {
			return;
		}
		mLogger.wtf(generateTag(), msg, tr);
	}
}
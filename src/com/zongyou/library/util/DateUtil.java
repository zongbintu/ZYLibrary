package com.zongyou.library.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 时间工具类
 * 
 * @author wangchaoyong
 * 
 *         2014-3-7 下午3:20:57
 */
@SuppressLint({ "SimpleDateFormat", "UseValueOf" })
public class DateUtil {

	/**
	 * 返回两个日期之间的详细日期数组(包括开始日期和结束日期)。 例如：2007-07-01 到2007-07-03 ,那么返回数组
	 * {"2007-07-01","2007-07-02","2007-07-03"}
	 * 
	 * @param startDate
	 *            格式"yyyy-MM-dd"
	 * @param endDate
	 *            格式"yyyy-MM-dd"
	 * @return 返回一个字符串数组对象
	 */
	public static String[] getArrayDiffDays(String startDate, String endDate) {
		int LEN = 0; // 用来计算两天之间总共有多少天
		// 如果结束日期和开始日期相同
		if (startDate.equals(endDate)) {
			return new String[] { startDate };
		}
		Date sdate = null;
		sdate = toDateDate(startDate); // 开始日期
		LEN = getDiffDays(startDate, endDate);
		String[] dateResult = new String[LEN + 1];
		dateResult[0] = startDate;
		for (int i = 1; i < LEN + 1; i++) {
			dateResult[i] = toStrDate(getDateAdd(sdate, i), "yyyy-MM-dd");
		}

		return dateResult;
	}

	/**
	 * 返回两个日期之间相差多少天。
	 * 
	 * @param startDate
	 *            格式"yyyy/MM/dd"
	 * @param endDate
	 *            格式"yyyy/MM/dd"
	 * @return 整数。
	 */
	public static int getDiffDays(String startDate, String endDate) {
		long diff = 0;
		Date sDate = toDateTime(startDate);
		Date eDate = toDateTime(endDate);
		diff = eDate.getTime() - sDate.getTime();
		diff = diff / 86400000;// 1000*60*60*24;
		return (int) diff;

	}

	/**
	 * 友好的方式显示时间
	 */
	public static String friendlyFormat(Date date) {
		if (date == null)
			return ":)";
		Calendar now = Calendar.getInstance();
		String time = toStrDate(date, "HH:mm");
		// 第一种情况，日期在同一天
		String curDate = getCurrentDateString();
		String paramDate = toStrTime(date);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((now.getTimeInMillis() - date.getTime()) / 3600000);
			if (hour > 0)
				return time;
			int minute = (int) ((now.getTimeInMillis() - date.getTime()) / 60000);
			if (minute < 2)
				return "刚刚";
			if (minute > 30)
				return "半个小时以前";
			return minute + "分钟前";
		}

		// 第二种情况，不在同一天
		int days = (int) ((getBegin(getCurrentDate()).getTime() - getBegin(date).getTime()) / 86400000);
		if (days == 1)
			return "昨天 " + time;
		if (days == 2)
			return "前天 " + time;
		if (days <= 7)
			return days + "天前";
		return toStrDate(paramDate, "MM-dd HH:mm");
	}

	/**
	 * 友好的方式显示时间
	 */
	public static String friendlyFormat(long date) {
		try {
			Calendar now = Calendar.getInstance();
			Date date2 = timeFormat.get().parse(toStrTime(date));
			String time = toStrDate(date2, "HH:mm");
			// 第一种情况，日期在同一天
			String curDate = getCurrentDateString();
			String paramDate = toStrTime(date);
			if (curDate.equals(paramDate)) {
				int hour = (int) ((now.getTimeInMillis() - date2.getTime()) / 3600000);
				if (hour > 0)
					return time;
				int minute = (int) ((now.getTimeInMillis() - date2.getTime()) / 60000);
				if (minute < 2)
					return time;
				if (minute > 30)
					return time;
				return time;
			}
			// 第二种情况，不在同一天
			int days = (int) ((getBegin(getCurrentDate()).getTime() - getBegin(date2).getTime()) / 86400000);
			if (days == 0)
				return "今天" + time;
			if (days == 1)
				return "昨天 " + time;
			if (days == 2)
				return "前天 " + time;
			if (days <= 7)
				return days + "天前";
			return toStrDate(paramDate, "MM-dd HH:mm");
		} catch (Exception e) {
			return "";
		}

	}

	/**
	 * 友好的方式显示时间
	 */
	public static String friendlyFormat(String str) {
		Date date = toDateTime(str);
		return friendlyFormat(date);
	}

	/**
	 * 返回日期的0点:2012-07-07 20:20:20 --> 2012-07-07 00:00:00
	 */
	public static Date getBegin(Date date) {
		return toDateDate(toStrDate(date) + " 00:00:00");
	}

	/**
	 * 获得当前日期固定间隔天数的日期，如前60天dateAdd(-60)
	 * 
	 * @param amount
	 *            距今天的间隔日期长度，向前为负，向后为正
	 * @param format
	 *            输出日期的格式.
	 * @return java.lang.String 按照格式输出的间隔的日期字符串.
	 */
	public static String getCurrentDateAdd(int amount, String format) {
		Date d = getDateAdd(getCurrentDate(), amount);
		return toStrDate(d, format);
	}

	/**
	 * 获得当前日期固定间隔天数的日期，如前60天dateAdd(-60)
	 * 
	 * @param amount
	 *            距今天的间隔日期长度，向前为负，向后为正
	 * @param format
	 *            输出日期的格式.
	 * @return java.lang.String 按照格式输出的间隔的日期字符串.
	 */
	public static String getDateFormatAdd(String date, int amount, String format) {
		Date d = getDateAdd(toDateTime(date), amount);
		return toStrDate(d, format);
	}

	/**
	 * 取得指定分隔符分割的年月日的日期对象.
	 * 
	 * @param args
	 * 
	 * @param split
	 *            时间格式的间隔符，例如“-”，“/”
	 * @return 一个java.util.Date()类型的对象
	 */
	public static Date getDateObj(String args, String split) {
		String[] temp = args.split(split);
		int year = new Integer(temp[0]).intValue();
		int month = new Integer(temp[1]).intValue();
		int day = new Integer(temp[2]).intValue();
		return getDate(year, month, day);
	}

	/**
	 * 
	 * @return 当前月份有多少天；
	 */
	public static int getDaysOfCurMonth() {
		int curyear = new Integer(getCurrentYear()).intValue(); // 当前年份
		int curMonth = new Integer(getCurrentMonth()).intValue();// 当前月份
		int mArray[] = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		// 判断闰年的情况 ，2月份有29天；
		if ((curyear % 400 == 0) || ((curyear % 100 != 0) && (curyear % 4 == 0))) {
			mArray[1] = 29;
		}
		return mArray[curMonth - 1];
		// 如果要返回下个月的天数，注意处理月份12的情况，防止数组越界；
		// 如果要返回上个月的天数，注意处理月份1的情况，防止数组越界；
	}

	/**
	 * 根据指定的年月 返回指定月份（yyyy-MM）有多少天。
	 * 
	 * @param time
	 *            yyyy-MM
	 * @return 天数，指定月份的天数。
	 */
	public static int getDaysOfCurMonth(String time) {
		time = replaceAll(time);
		String[] timeArray = time.split("-");
		int curyear = new Integer(timeArray[0]).intValue(); // 当前年份
		int curMonth = new Integer(timeArray[1]).intValue();// 当前月份
		int mArray[] = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		// 判断闰年的情况 ，2月份有29天；
		if ((curyear % 400 == 0) || ((curyear % 100 != 0) && (curyear % 4 == 0))) {
			mArray[1] = 29;
		}
		if (curMonth == 12) {
			return mArray[0];
		}
		return mArray[curMonth - 1];
	}

	/**
	 * 根据指定的年、月、日返回当前是星期几。1表示星期天、2表示星期一、7表示星期六。
	 * 
	 * @param year
	 * @param month
	 *            month是从1开始的12结束
	 * @param day
	 * @return 返回一个代表当期日期是星期几的数字。1表示星期天、2表示星期一、7表示星期六。
	 */
	public static int getDayOfWeek(String year, String month, String day) {
		Calendar cal = new GregorianCalendar(new Integer(year).intValue(), new Integer(month).intValue() - 1,
				new Integer(day).intValue());
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 根据指定的年、月、日返回当前是星期几。1表示星期天、2表示星期一、7表示星期六。
	 * 
	 * @param date
	 *            "yyyy/MM/dd",或者"yyyy-MM-dd"
	 * @return 返回一个代表当期日期是星期几的数字。1表示星期天、2表示星期一、7表示星期六。
	 */
	public static int getDayOfWeek(String date) {
		String[] temp = null;
		if (date.indexOf("/") > 0) {
			temp = date.split("/");
		}
		if (date.indexOf("-") > 0) {
			temp = date.split("-");
		}
		return getDayOfWeek(temp[0], temp[1], temp[2]);
	}

	/**
	 * 根据指定的年、月、日返回当前是星期几。1表示星期天、2表示星期一、7表示星期六。
	 * 
	 * @param date
	 *            "yyyy/MM/dd",或者"yyyy-MM-dd"
	 * @param split
	 *            分隔符
	 * @return 返回一个代表当期日期是星期几的数字。1表示星期天、2表示星期一、7表示星期六。
	 */
	public static int getDayOfWeek(String date, String split) {
		String[] temp = date.split(split);
		return getDayOfWeek(temp[0], temp[1], temp[2]);
	}

	/**
	 * 返回当前日期是星期几。例如：星期日、星期一、星期六等等。
	 * 
	 * @param date
	 *            格式为 yyyy/MM/dd 或者 yyyy-MM-dd
	 * @return 返回当前日期是星期几
	 */
	public static String getChinaDayOfWeek(String date) {
		int week = getDayOfWeek(date);
		return weeks[week - 1];
	}

	/**
	 * 返回当前日期是星期几。例如：星期日、星期一、星期六等等。
	 * 
	 * @param date
	 *            格式为 yyyy/MM/dd 或者 yyyy-MM-dd
	 * @return 返回当前日期是星期几
	 */
	public static String getChinaDayOfWeek(Date date) {
		int week = getDayOfWeek(date);
		return weeks[week - 1];
	}

	/**
	 * 返回当前日期是星期几。例如：星期日、星期一、星期六等等。
	 * 
	 * @param date
	 * @param split
	 *            分割符
	 * @return 返回当前日期是星期几
	 */
	public static String getChinaDayOfWeek(String date, String split) {
		int week = getDayOfWeek(date, split);
		return weeks[week - 1];
	}

	/**
	 * 根据指定的年、月、日返回当前是星期几。1表示星期天、2表示星期一、7表示星期六。
	 * 
	 * @param date
	 * @return 返回一个代表当期日期是星期几的数字。1表示星期天、2表示星期一、7表示星期六。
	 */
	public static int getDayOfWeek(Date date) {
		Calendar begincal = GregorianCalendar.getInstance();
		begincal.setTime(date);
		return begincal.get(GregorianCalendar.DAY_OF_WEEK);
	}

	/**
	 * 获得当前日期固定间隔天数的日期，如前60天dateAdd(-60)
	 * 
	 * @param amount
	 *            距今天的间隔日期长度，向前为负，向后为正
	 * @param format
	 *            输出日期的格式.
	 * @return java.lang.String 按照格式输出的间隔的日期字符串.
	 */
	public static String getDateFormatAdd(Date date, int amount, String format) {
		Date d = getDateAdd(date, amount);
		return toStrDate(d, format);
	}

	/**
	 * 取得给定日期加上一定天数后的日期对象.
	 * 
	 * @param date
	 *            给定的日期对象
	 * @param amount
	 *            需要添加的天数，如果是向前的天数，使用负数就可以.
	 * @return Date 加上一定天数以后的Date对象.
	 */
	public static Date getDateAdd(Date date, int amount) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.DATE, amount);
		return cal.getTime();
	}

	/**
	 * 取得给定日期加上一定天数后的日期对象.
	 * 
	 * @param date
	 *            给定的日期对象
	 * @param amount
	 *            需要添加的天数，如果是向前的天数，使用负数就可以.
	 * @return Date 加上一定天数以后的Date对象.
	 */
	public static Date getMinuteAdd(Date date, int amount) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.MINUTE, amount);
		return cal.getTime();
	}

	/**
	 * 取得给定日期加上一定天数后的日期对象.
	 * 
	 * @param date
	 *            给定的日期对象
	 * @param amount
	 *            需要添加的天数，如果是向前的天数，使用负数就可以.
	 * @return Date 加上一定天数以后的Date对象.
	 */
	public static Date getDateAdd(String date, int amount) {
		date = replaceAll(date);
		Calendar cal = new GregorianCalendar();
		cal.setTime(toDateTime(date));
		cal.add(GregorianCalendar.DATE, amount);
		return cal.getTime();
	}

	/**
	 * 返回当前时间串 格式:yyMMddhhmmss,在上传附件时使用
	 * 
	 * @return String
	 */
	public static String getCurDate() {
		GregorianCalendar gcDate = new GregorianCalendar();
		int month = gcDate.get(GregorianCalendar.MONTH) + 1;
		int day = gcDate.get(GregorianCalendar.DAY_OF_MONTH);
		int hour = gcDate.get(GregorianCalendar.HOUR_OF_DAY);
		int minute = gcDate.get(GregorianCalendar.MINUTE);
		int sen = gcDate.get(GregorianCalendar.SECOND);
		StringBuffer sb = new StringBuffer();
		sb.append(gcDate.get(GregorianCalendar.YEAR));
		if (month < 10)
			sb.append("0");
		sb.append(month);
		if (day < 10)
			sb.append("0");
		sb.append(day);

		if (hour < 10)
			sb.append("0");
		sb.append(hour);

		if (minute < 10)
			sb.append("0");
		sb.append(minute);

		if (sen < 10)
			sb.append("0");
		sb.append(sen);
		return sb.toString();
	}

	/**
	 * 获取小时/分钟/秒
	 * 
	 * @param second
	 *            秒
	 * @return 包含小时、分钟、秒的时间字符串，例如3小时23分钟13秒。
	 */
	public static String getHour(long second) {
		long hour = second / 60 / 60;
		long minute = (second - hour * 60 * 60) / 60;
		StringBuffer sb = new StringBuffer();
		sb.append(hour);
		sb.append("小时");
		sb.append(minute);
		sb.append("分钟");
		sb.append((second - hour * 60 * 60) - minute * 60);
		sb.append("秒");
		return sb.toString();
	}

	/**
	 * 字符串对象转换为date对象
	 * 
	 * @param dateStr
	 *            日期描述
	 * @return 给定字符串描述的日期对象。
	 */
	public static Date toDateDate(long dateStr) {
		try {
			return dateFormat.get().parse(toStrDate(dateStr));
		} catch (ParseException e) {
			return getCurrentDate();
		}
	}

	/**
	 * 字符串对象转换为date对象
	 * 
	 * @param dateStr
	 *            日期描述
	 * @return 给定字符串描述的日期对象。
	 */
	public static Date toDateDate(String dateStr) {
		try {
			dateStr = replaceAll(dateStr);
			return dateFormat.get().parse(dateStr);
		} catch (ParseException e) {
			return getCurrentDate();
		}
	}

	/**
	 * 字符串对象转换为date对象
	 * 
	 * @param dateStr
	 *            日期描述
	 * @return 给定字符串描述的日期对象。StrByTimeGodata
	 */
	public static Date toDateTime(String dateStr) {
		try {
			dateStr = replaceAll(dateStr);
			return timeFormat.get().parse(dateStr);
		} catch (ParseException e) {
			return toDateDate(dateStr);
		}
	}

	/** 日期格式 */
	private final static ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	/** 时间格式 */
	private final static ThreadLocal<SimpleDateFormat> timeFormat = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	/**
	 * 时间格式 获得string类型的系统当前时间 yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getCurrentDateString() {
		return dateFormat.get().format(getCurrentDate());
	}

	/**
	 * date转换为stringyyyy-MM-dd
	 * 
	 * @param date
	 *            传入的时间
	 * @return
	 */
	public static String toStrDate(Date date) {
		return dateFormat.get().format(date);
	}

	/**
	 * date转换为stringyyyy-MM-dd
	 * 
	 * @param date
	 *            传入的时间
	 * @return
	 */
	public static String toStrDate(long date) {
		return dateFormat.get().format(date);
	}

	/**
	 * 根据传入格式
	 * 
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String formatDate(String pattern, long date) {
		SimpleDateFormat f = new SimpleDateFormat(pattern);
		return f.format(new Date(date));
	}

	/**
	 * date转换为stringyyyy-MM-dd
	 * 
	 * @param date
	 *            传入的时间
	 * @return
	 */
	public static String toStrDate(String date) {
		date = replaceAll(date);
		return dateFormat.get().format(toDateDate(date));
	}

	/**
	 * 
	 * @param date
	 * @param format
	 *            格式
	 * @return
	 */
	public static String toStrDate(String date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(toDateTime(date));
	}

	/**
	 * 
	 * @param date
	 * @param format
	 *            格式
	 * @return
	 */
	public static String toStrDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * date转换为string yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            传入的时间
	 * @return
	 */
	public static String toStrTime(Date date) {
		return timeFormat.get().format(date);
	}

	/**
	 * date转换为string yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            传入的时间
	 * @return
	 */
	public static String toStrTime(long date) {
		return timeFormat.get().format(date);
	}

	/**
	 * date转换为string yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            传入的时间
	 * @return
	 */
	public static String toStrTime(String date) {
		date = replaceAll(date);
		return timeFormat.get().format(date);
	}

	/**
	 * 获得string类型的系统当前时间(time) yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getCurrentTimeString() {
		return timeFormat.get().format(getCurrentDate());
	}

	/**
	 * 获取两个时间串时间的差值，单位为秒
	 * 
	 * @param startTime
	 *            开始时间 yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 *            结束时间 yyyy-MM-dd HH:mm:ss
	 * @return 两个时间的差值(秒)
	 */
	public static long getDiff(String startTime, String endTime) {
		long diff = 0;
		Date startDate = toDateTime(startTime);
		Date endDate = toDateTime(endTime);
		diff = startDate.getTime() - endDate.getTime();
		return diff = diff / 1000;
	}

	/**
	 * 取得当前日期的年份，以yyyy格式返回.
	 * 
	 * @return 当年 yyyy
	 */
	public static int getCurrentYearInt() {
		return Integer.valueOf(getCurrentDate("yyyy"));
	}

	/**
	 * 取得当前日期的月份，以MM格式返回.
	 * 
	 * @return 当前月份 MM
	 */
	public static int getCurrentMonthInt() {
		return Integer.valueOf(getCurrentDate("MM"));
	}

	/**
	 * 取得当前日期的小时，以hh格式返回.
	 * 
	 * @return 当前月份 MM
	 */
	public static int getCurrentHoursInt() {
		return Integer.valueOf(getCurrentDate("HH"));
	}

	/**
	 * 取得当前日期的分钟，以ss格式返回.
	 * 
	 * @return 当前月份 ss
	 */
	public static int getCurrentMinutesInt() {
		return Integer.valueOf(getCurrentDate("ss"));
	}

	/**
	 * 取得当前日期的天数，以格式"dd"返回.
	 * 
	 * @return 当前月中的某天dd
	 */
	public static int getCurrentDayInt() {
		return Integer.valueOf(getCurrentDate("dd"));
	}

	/**
	 * 取得当前日期的年份，以yyyy格式返回.
	 * 
	 * @return 当年 yyyy
	 */
	public static String getCurrentYear() {
		return getCurrentDate("yyyy");
	}

	/**
	 * 取得当前日期的月份，以MM格式返回.
	 * 
	 * @return 当前月份 MM
	 */
	public static String getCurrentMonth() {
		return getCurrentDate("MM");
	}

	/**
	 * 取得当前日期的小时，以hh格式返回.
	 * 
	 * @return 当前月份 MM
	 */
	public static String getCurrentHours() {
		return getCurrentDate("HH");
	}

	/**
	 * 取得当前日期的分钟，以ss格式返回.
	 * 
	 * @return 当前月份 ss
	 */
	public static String getCurrentMinutes() {
		return getCurrentDate("ss");
	}

	/**
	 * 取得当前日期的天数，以格式"dd"返回.
	 * 
	 * @return 当前月中的某天dd
	 */
	public static String getCurrentDay() {
		return getCurrentDate("dd");
	}

	/**
	 * 根据指定格式返回系统当前时间
	 * 
	 * @param format
	 *            时间格式
	 * @return string类型时间
	 */
	public static String getCurrentDate(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(getCurrentDate());
	}

	/**
	 * 根据指定时间和指定格式返回指定格式的String类型时间
	 * 
	 * @param format
	 *            时间格式
	 * @return string类型时间
	 */
	public static Date transitionDate(String date, String format) {
		date = replaceAll(date);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 根据指定的年月日小时分秒，返回一个java.Util.Date对象。
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月 0-11
	 * @param date
	 *            日
	 * @param hourOfDay
	 *            小时 0-23
	 * @param minute
	 *            分 0-59
	 * @param second
	 *            秒 0-59
	 * @return 一个Date对象。
	 */
	public static Date getDate(int year, int month, int date, int hourOfDay, int minute, int second) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(year, month, date, hourOfDay, minute, second);
		return cal.getTime();
	}

	public static String addTime() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(Calendar.MINUTE, cal.getTime().getMinutes() + 30);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(cal.getTime());
	}

	/**
	 * 返回指定日期的后一天。
	 * 
	 * @param sourceDate
	 * @param format
	 * @return 返回日期字符串，形式和formcat一致。
	 */
	public static String getFormatDateTommorrow(String sourceDate, String format) {
		return getFormatDateAdd(transitionDate(sourceDate, format), 1, format);
	}

	/**
	 * 取得给定日期加上一定天数后的日期对象.
	 * 
	 * @param date
	 *            给定的日期对象
	 * @param amount
	 *            需要添加的天数，如果是向前的天数，使用负数就可以.
	 * @param format
	 *            输出格式.
	 * @return Date 加上一定天数以后的Date对象.
	 */
	public static String getFormatDateAdd(Date date, int amount, String format) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.DATE, amount);
		return toStrDate(cal.getTime(), format);
	}

	/**
	 * 求两个日期相差天数
	 * 
	 * @param sd
	 *            起始日期，格式yyyy-MM-dd
	 * @param ed
	 *            终止日期，格式yyyy-MM-dd
	 * @return 两个日期相差天数
	 */
	public static long getIntervalDays(String sd, String ed) {
		sd = replaceAll(sd);
		ed = replaceAll(ed);
		return ((java.sql.Date.valueOf(ed)).getTime() - (java.sql.Date.valueOf(sd)).getTime()) / (3600 * 24 * 1000);
	}

	/**
	 * 起始年月yyyy-MM与终止月yyyy-MM之间跨度的月数
	 * 
	 * @return int
	 */
	public static int getInterval(String beginMonth, String endMonth) {
		Calendar begincal = GregorianCalendar.getInstance();
		begincal.setTime(transitionDate(beginMonth, "yyyy-mm"));
		Calendar endcal = GregorianCalendar.getInstance();
		endcal.setTime(transitionDate(endMonth, "yyyy-mm"));
		int intBeginYear = begincal.get(GregorianCalendar.YEAR);
		int intBeginMonth = begincal.get(GregorianCalendar.MONTH);
		int intEndYear = endcal.get(GregorianCalendar.YEAR);
		int intEndMonth = endcal.get(GregorianCalendar.MONTH);
		return ((intEndYear - intBeginYear) * 12) + (intEndMonth - intBeginMonth);
	}

	/**
	 * 根据指定的年月日小时分秒，返回一个java.Util.Date对象。
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月 0-11
	 * @param date
	 *            日
	 * @return 一个Date对象。
	 */
	public static Date getDate(int year, int month, int date) {
		return getDate(year, month, date, 0, 0, 0);
	}

	/**
	 * 获取当前时间:Date
	 */
	public static Date getCurrentDate() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 得到输入时间的年份
	 * 
	 * @param date
	 * @return
	 */
	public static int getDateYear(String date) {
		Calendar begincal = GregorianCalendar.getInstance();
		begincal.setTime(transitionDate(date, "yyyy-mm-dd"));
		return begincal.get(GregorianCalendar.YEAR);
	}

	/**
	 * 得到输入时间的月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getDateMonedh(String date) {
		Calendar begincal = GregorianCalendar.getInstance();
		begincal.setTime(transitionDate(date, "yyyy-mm-dd"));
		return begincal.get(GregorianCalendar.MONTH);
	}

	/**
	 * 得到输入时间的号数
	 * 
	 * @param date
	 * @return
	 */
	public static int getDateDay(String date) {
		Calendar begincal = GregorianCalendar.getInstance();
		begincal.setTime(transitionDate(date, "yyyy-mm-dd"));
		return begincal.get(GregorianCalendar.DAY_OF_MONTH);
	}

	/**
	 * 得到输入时间的年份
	 * 
	 * @param date
	 * @return
	 */
	public static int getDateYear(Date date) {
		Calendar begincal = GregorianCalendar.getInstance();
		begincal.setTime(date);
		return begincal.get(GregorianCalendar.YEAR);
	}

	/**
	 * 得到输入时间的月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getDateMonedh(Date date) {
		Calendar begincal = GregorianCalendar.getInstance();
		begincal.setTime(date);
		return begincal.get(GregorianCalendar.MONTH);
	}

	/**
	 * 得到输入时间的号数
	 * 
	 * @param date
	 * @return
	 */
	public static int getDateDay(Date date) {
		Calendar begincal = GregorianCalendar.getInstance();
		begincal.setTime(date);
		return begincal.get(GregorianCalendar.DAY_OF_MONTH);
	}

	/**
	 * 分隔符替换，主要是支持yyyy-mm-dd 和yyyy/mm/dd两种格式的时间
	 * 
	 * @param str
	 * @return
	 */
	private static String replaceAll(String str) {
		if (str.indexOf("/") > 0) {
			return str.replaceAll("/", "-");
		} else {
			return str;
		}
	}
	/**
	 * UTC 2 GMT
	 * @param pattern
	 * @param time
	 * @return
	 */
	public static String UTC2GMT(String pattern,long time){
		String str = formatDate(pattern, time);
		SimpleDateFormat format=new SimpleDateFormat(pattern);
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		try {
			Date newDate = format.parse(str);
			return toStrDate(newDate, pattern);
		} catch (ParseException e) {
			LogUtils.e("date format error",e);
		}
		return str;
	}
	
	/**
	 * 时间戳转化
	 * @param time
	 * @return
	 */
	
	public static long  GetUTCTime(String pattern,long time){
		
		String str = formatDate(pattern, time);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date newDate;
		try {
			newDate = format.parse("yyyy-MM-dd HH:mm:ss");
			return	newDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return 0;
		
	}
	/**
	 * 获取当前UTC时间
	 * @return
	 */
	
	 public static long getUTCTimeStr() {  
	        // 1、取得本地时间：  
	        Calendar cal = Calendar.getInstance() ;  
	        // 2、取得时间偏移量：  
	        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);  
	        // 3、取得夏令时差：  
	        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);  
	        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：  
	        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));  
	        
	        return cal.getTime().getTime();  
	    }
	public  static long date2longtime(String pattern,String type) {
		SimpleDateFormat format = new SimpleDateFormat( type );
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		long  datetime = 0;
		try {
			Date	date = format.parse(pattern);
			datetime=date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return datetime;
	}
	/**
	 * GMT
	 */
	public static String formatGMTDate(String pattern, long date) {
		SimpleDateFormat f = new SimpleDateFormat(pattern);
		f.setTimeZone(TimeZone.getTimeZone("GMT"));
		return f.format(new Date(date));
	}
	private static final String[] weeks = new String[] { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
}

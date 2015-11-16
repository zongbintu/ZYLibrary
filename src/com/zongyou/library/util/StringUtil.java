package com.zongyou.library.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串验证辅助类
 * 
 * @author wangchaoyong
 * 
 */
public class StringUtil {
	/**
	 * 获取异常的详细信息
	 * 
	 * @param e
	 * @return
	 */
	public static String getMessage(Exception e) {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		e.printStackTrace(new PrintWriter(buf, true));
		String expMessage = buf.toString();
		return expMessage;
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串是否为空，如果为空则重新赋值为""
	 * 
	 * @param par
	 *            需要判断的字符串
	 * @return
	 */
	public static String judgeString(String par) {
		if (!hasText(par))
			return "";
		return par;
	}

	/**
	 * 判断输入的字符串是否全中文
	 * 
	 * @param str
	 * @return true 为真 false 为假
	 */
	public static boolean checkChs(String str) {
		boolean mark = false;
		Pattern pattern = Pattern.compile("[\u4E00-\u9FA5]");
		Matcher matc = pattern.matcher(str);
		StringBuffer stb = new StringBuffer();
		while (matc.find()) {
			mark = true;
			stb.append(matc.group());
		}
		if (stb.length() != str.length()) {
			mark = false;
		}
		return mark;
	}

	/**
	 * 判断输入的字符串是否包含中文
	 * 
	 * @param str
	 * @return true 为包含 false 为不包含
	 */
	public static boolean isCheckChs(String str) {
		boolean mark = false;
		Pattern pattern = Pattern.compile("[\u4E00-\u9FA5]");
		Matcher matc = pattern.matcher(str);
		while (matc.find()) {
			mark = true;
			return mark;
		}
		return mark;
	}

	/**
	 * 字符串是否为空
	 * 
	 * @param str字符串
	 * @return true 为真 false 为假
	 */
	private static boolean hasLength(CharSequence str) {
		return (str != null && !str.equals("") && !str.equals("null") && str.length() > 0);
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            字符串
	 * @return true 为真不为null false 为null字符串包括空格
	 */
	public static boolean hasText(CharSequence str) {
		if (!hasLength(str))
			return false;
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证邮箱格式
	 * 
	 * @param strEmail
	 * @return
	 */
	public static boolean isEmail(String strEmail) {
		String strPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail);
		return m.matches();
	}

	/**
	 * 验证手机格式
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobPhnNum) {
		/*
		 * 移动: 2G号段(GSM网络)有139,138,137,136,135,134,159,158,152,151,150,
		 * 3G号段(TD-SCDMA网络)有157,182,183,188,187 147是移动TD上网卡专用号段. 联通:
		 * 2G号段(GSM网络)有130,131,132,155,156 3G号段(WCDMA网络)有186,185 电信:
		 * 2G号段(CDMA网络)有133,153 3G号段(CDMA网络)有189,180
		 */
		String YD = "^[1]{1}(([3]{1}[4-9]{1})|([5]{1}[012789]{1})|([8]{1}[2378]{1})|([4]{1}[7]{1}))[0-9]{8}$";
		String LT = "^[1]{1}(([3]{1}[0-2]{1})|([5]{1}[56]{1})|([8]{1}[56]{1}))[0-9]{8}$";
		String DX = "^[1]{1}(([3]{1}[3]{1})|([5]{1}[3]{1})|([8]{1}[09]{1}))[0-9]{8}$";
		// 判断手机号码是否是11位
		if (mobPhnNum.length() == 11) {
			// 判断手机号码是否符合中国移动的号码规则
			if (mobPhnNum.matches(YD) || mobPhnNum.matches(LT) || mobPhnNum.matches(DX)) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}

	}

	/**
	 * 获取随机数
	 * 
	 * @param i
	 *            随机数的个数
	 * @return 生成后的随机数
	 */
	public static String getRandom(int i) {
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < i; j++) {
			String readomWord = "0123456789qazxswedcvfrtgbnhyujmkiolp";
			int readomWordIndex = (int) (Math.random() * (readomWord.length() - 1));
			sb.append(readomWord.charAt(readomWordIndex));
		}
		return sb.toString();
	}

	/**
	 * 功能：身份证的有效验证
	 * 
	 * @param IDStr
	 *            身份证号
	 * @return 有效：返回"" 无效：返回String信息
	 * @throws ParseException
	 */
	public static String IDCardValidate(String IDStr) {
		IDStr = IDStr.toLowerCase();
		StringBuffer errorInfo = new StringBuffer();// 记录错误信息
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			errorInfo.append("身份证号码长度应该为15位或18位。");
			return errorInfo.toString();
		}
		// =======================(end)========================

		// ================ 数字 除最后以为都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			errorInfo.append("身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。");
			return errorInfo.toString();
		}
		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		StringBuffer str = new StringBuffer(strYear).append("-").append(strMonth).append("-").append(strDay);
		if (isDataFormat(str.toString()) == false) {
			errorInfo.append("身份证生日无效。");
			return errorInfo.toString();
		}
		GregorianCalendar gc = new GregorianCalendar();
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150 || (System.currentTimeMillis() - DateUtil.toDateDate(str.toString()).getTime()) < 0) {
				errorInfo.append("身份证生日不在有效范围。");
				return errorInfo.toString();
			}
		} catch (NumberFormatException e) {
			return "身份证生日错误.";
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			errorInfo.append("身份证月份无效");
			return errorInfo.toString();
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			errorInfo.append("身份证日期无效");
			return errorInfo.toString();
		}
		// =====================(end)=====================

		// ================ 地区码时候有效 ================
		Hashtable h = GetAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			errorInfo.append("身份证地区编码错误。");
			return errorInfo.toString();
		}
		// ==============================================

		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				errorInfo.append("身份证无效，不是合法的身份证号码");
				return errorInfo.toString();
			}
		} else {
			return "";
		}
		return "";
	}

	/**
	 * 功能：判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 功能：设置地区编码
	 * 
	 * @return Hashtable 对象
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Hashtable GetAreaCode() {
		Hashtable hashtable = new Hashtable();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/**
	 * 验证日期字符串是否是YYYY-MM-DD格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDataFormat(String str) {
		boolean flag = false;
		String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern pattern1 = Pattern.compile(regxStr);
		Matcher isNo = pattern1.matcher(str);
		if (isNo.matches()) {
			flag = true;
		}
		return flag;
	}

	public static Double getInt(String str) {
		if (hasText(str)) {
			return Double.parseDouble(str);
		} else {
			return 0.00;
		}
	}

	public static String mobileHide(String str) {
		return hidden(str, 4, '*');
	}

	public static String hidden(String str, int count, char replacement) {
		if (str == null || count < 1) {
			return str;
		}
		char[] chs = str.toCharArray();
		int offset = 0;
		if (chs.length - count > 0) {
			offset = (chs.length - count) / 2;
		}
		int end = Math.min(offset + count, chs.length);
		while (offset < end) {
			chs[offset++] = replacement;
		}
		return new String(chs);
	}
}

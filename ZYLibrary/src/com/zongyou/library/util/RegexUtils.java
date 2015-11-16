package com.zongyou.library.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类名：RegexUtils
 * <p>
 * 正则匹配工具
 * </p>
 *
 * @author zbtu
 * @version 1.0 2013-10-15
 */
public class RegexUtils {

    public static final String DATE_REGEX = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";

    /**
     * 登陆用户名和密码正则表达式
     */
    public static final String USER_NAME_REGEX = "^[a-zA-Z0-9\\-]{1,15}";

    public static final String PASS_WORD_REGEX = "^[a-zA-Z0-9]{6,15}";
    /** 验证手机号码 */
    // public static final String
    // MOBILE_REGEX="^((\\+86)|(86))?[1][3578]\\d{9}$";

    /**
     * 验证手机号码
     */
    public static final String MOBILE_REGEX = "[1]\\d{10}$";
    /**
     * 验证电话 8位数字
     */
    public static final String TEL_REGEX = "\\d{8}$";
    public static final String POSITIVE_INTEGER = "^\\d+$";

    public static final String URL_REGEX = "((http|ftp|https):\\/\\/)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(\\/[a-zA-Z0-9\\&%_\\./-~-]*)?";

    // 校验Tag Alias 只能是数字,英文字母和中文
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public static boolean match(String pattern, String src) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(src);
        return m.matches();
    }

    /**
     * 校验手机号码
     *
     * @param mobile
     * @return
     */
    public static boolean isValidMobileNo(String mobile) {
        return Pattern.matches(MOBILE_REGEX, mobile);
    }

    /**
     * 校验8位电话号码
     *
     * @param tel
     * @return
     */
    public static boolean isValidTel(String tel) {
        return Pattern.matches(TEL_REGEX, tel);
    }

    /**
     * 日期匹配(例如:2013-10-15)
     *
     * @param date 要匹配的日期字符串
     * @return boolean true 表示匹配成功, false表示匹配失败
     * @Since 1.0
     * @author zbtu
     * @date 2013-10-15
     */
    public static boolean checkDate(String date) {
        return Pattern.matches(DATE_REGEX, date);
    }

    /**
     * 根据输入的内容以及正则表达式来匹配输入内容中的指定字符, 例如 正则表达式^[0-9A-Z]{2}匹配出了3U8834中的3U
     *
     * @param content 输入的内容
     * @param regex   正则表达式
     * @return 匹配后得到的字符串
     * @Since 1.0
     * @author zbtu
     * @date 2013-10-15
     */
    public static String getRegexString(String content, String regex) {
        Pattern pt = Pattern.compile(regex);
        Matcher matcher = pt.matcher(content);
        String result = "";
        while (matcher.find()) {
            result += content.substring(matcher.start(), matcher.end());
        }
        return result.substring(0, result.length());
    }

    /**
     * 用户名匹配
     *
     * @param userName 用户名
     * @return boolean true表示匹配成功，false表示匹配失败
     * @throws 无
     * @author zbtu
     * @date 2013-10-29
     * @since 1.0
     */
    public static boolean checkUserName(String userName) {
        return Pattern.matches(USER_NAME_REGEX, userName);
    }

    /**
     * 登录密码匹配
     *
     * @param passWord 登录密码
     * @return boolean true表示匹配成功，false表示匹配失败
     */
    public static boolean checkPassWord(String passWord) {
        return Pattern.matches(PASS_WORD_REGEX, passWord);
    }
}

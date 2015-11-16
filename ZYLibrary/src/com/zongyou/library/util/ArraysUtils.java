package com.zongyou.library.util;

import java.util.Collection;

/**
 * 集合工具类
 * 
 * @author Altas
 * @email Altas.Tu@qq.com
 * @date 2014年9月24日
 */
public class ArraysUtils {
	/**
	 * 将简单类型集合转换成1,2,3的形式输出
	 * 
	 * @param collection
	 * @return
	 */
	public static String toString(Collection collection) {
		return collection.toString().replace("[", "").replace("]", "");
	}

	/**
	 * 判断集合是否为空
	 * 
	 * @param collection
	 * @return is null or isempty return true,otherwise false
	 */
	public static boolean isEmpty(Collection collection) {
		return null == collection || collection.isEmpty();
	}

}

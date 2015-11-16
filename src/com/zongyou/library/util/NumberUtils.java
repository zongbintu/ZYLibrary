package com.zongyou.library.util;

import java.text.DecimalFormat;

/**
 * @author Altas
 * @email Altas.Tutu@gmail.com
 * @time Dec 31, 2014 4:07:21 PM
 */
public class NumberUtils {
	//Rounding mode where positive values are rounded towards positive infinity and negative values towards negative infinity.
	public static final int ROUND_UP=0x00000000;
	//Rounding mode where the values are rounded towards zero.
	public static final int ROUND_DOWN=0x00000001;


	/**
	 * 保留两位小数
	 * @param src
	 * @return
	 */
	public static String formatDecimal2(double src){
		
		return formatDecimal2(src,ROUND_UP);
	}
	public static String formatDecimal2(double src,int roundingMode){
		src= ROUND_DOWN == roundingMode?src-0.005:src;
		if(0 ==src)
			return "0.00";
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		return decimalFormat.format(src);
	}
	/**
	 * 百分比
	 * @param y
	 * @param z
	 * @return
	 */
	public static String getPercent(double y, double z) {
		double fen = y / z;
		DecimalFormat df1 = new DecimalFormat("##.00%");
		return df1.format(fen);
	}
		/**
	 * 百分比
	 * @param y
	 * @param z
	 * @param pattern
	 * @return
	 */
	public static String getPercent(double y, double z,String pattern) {
		double fen = y / z;
		DecimalFormat df1 = new DecimalFormat(pattern);
		return df1.format(fen);
	}
}

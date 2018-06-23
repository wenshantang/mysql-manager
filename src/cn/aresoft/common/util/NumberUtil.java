package cn.aresoft.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {
	/**
	 * 总长度16位数值,decimal(16,8)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMoney16(String str) {
		Pattern pattern = Pattern.compile("^(([1-9]{1}\\d{0,7})|([0]{1}))(\\.(\\d){0,8})?$");
		Matcher match = pattern.matcher(str);
		return match.matches();
	}

	/**
	 * 总长度6位数值,double(6,4)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMoney6(String str) {
		Pattern pattern = Pattern.compile("^(([1-9]{1}\\d{0,1})|([0]{1}))(\\.(\\d){0,4})?$");
		Matcher match = pattern.matcher(str);
		return match.matches();
	}

	/**
	 * 总长度10位数值,double(10,2)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMoney10(String str) {
		Pattern pattern = Pattern.compile("^(([1-9]{1}\\d{0,7})|([0]{1}))(\\.(\\d){0,2})?$");
		Matcher match = pattern.matcher(str);
		return match.matches();
	}

	/**
	 * 总长度12位数值,double(12,2)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMoney12(String str) {
		Pattern pattern = Pattern.compile("^(([1-9]{1}\\d{0,9})|([0]{1}))(\\.(\\d){0,2})?$");
		Matcher match = pattern.matcher(str);
		return match.matches();
	}

	/**
	 * 总长度9位数值,double(9,4)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMoney9(String str) {
		Pattern pattern = Pattern.compile("^(([1-9]{1}\\d{0,4})|([0]{1}))(\\.(\\d){0,4})?$");
		Matcher match = pattern.matcher(str);
		return match.matches();
	}

	/**
	 * 总长度5位数值,double(5,4)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMoney5(String str) {
		Pattern pattern = Pattern.compile("^((\\d{1}))(\\.(\\d){0,4})?$");
		Matcher match = pattern.matcher(str);
		return match.matches();
	}

	/**
	 * 总长度7位数值,double(7,4)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMoney7(String str) {
		Pattern pattern = Pattern.compile("^(([1-9]{1}\\d{0,2})|([0]{1}))(\\.(\\d){0,4})?$");
		Matcher match = pattern.matcher(str);
		return match.matches();
	}

	/**
	 * 6位邮编
	 * 
	 * @param str
	 * @return
	 */
	public static boolean postCode(String str) {
		Pattern pattern = Pattern.compile("^\\d{6}$");
		Matcher match = pattern.matcher(str);
		return match.matches();
	}

	/**
	 * 格式化数值，精确到小数点后4位"0.00##",至少2位小数。
	 * 
	 * @param numberStr
	 * @return
	 */
	public static String toNumberDot8Str(String numberStr) {
		try {
			DecimalFormat df = new DecimalFormat("0.00##");
			return df.format(Double.valueOf(numberStr));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "0";
	}

	/**
	 * 格式化数值，精确到小数点后2位"0.00",2位小数。
	 * 
	 * @param numberStr
	 * @return
	 */
	public static String toNumberDot8Str2(String numberStr) {
		try {
			DecimalFormat df = new DecimalFormat("0.00");
			return df.format(Double.valueOf(numberStr));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "0";
	}

	/**
	 * 格式化小数百分比，精确到小数点后2位"0.00"
	 * 
	 * @param numberStr
	 * @return
	 */
	public static String toNumberPercentDot2Str(String numberStr) {
		try {
			/*
			 * NumberFormat nf = NumberFormat.getPercentInstance();
			 * nf.setMinimumFractionDigits( 2 ); return
			 * nf.format(Double.valueOf(numberStr));
			 */
			DecimalFormat df = new DecimalFormat("0.00%");
			return df.format(Double.valueOf(numberStr));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "0%";
	}

	/**
	 * 截取格式化字符串类型数字
	 * 
	 * @param number
	 *            传入数字
	 * @param flag
	 *            截取保留位数[0~4],超出范围取0
	 * @return
	 */
	public static String convertSubDot(String number, int flag) {
		number = number.replaceAll(",", "");
		String fmt = "#,###";
		if (flag <= 0 || flag > 4)
			flag = 0;
		if (flag >= 1) {
			fmt += ".";
			for (int i = 0; i < flag; i++) {
				fmt += "0";
			}
		}
		DecimalFormat df = new DecimalFormat(fmt);
		BigDecimal b = new BigDecimal(number).setScale(flag, BigDecimal.ROUND_DOWN);// 直接删除多余的小数位，如2.35会变成2.3
		String decimalStr = df.format(b.doubleValue()) + "";
		if (decimalStr.startsWith(".")) {
			decimalStr = "0" + decimalStr;
		}
		return decimalStr;
	}

	/**
	 * 截取格式化字符串类型数字
	 * 
	 * @param number
	 *            传入数字
	 * @param flag
	 *            截取保留位数[0~4],超出范围取0
	 * @return
	 */
	public static String convertSubDots(String number, int flag) {
		number = number.replaceAll(",", "");
		String fmt = "##";
		if (flag <= 0 || flag > 4)
			flag = 0;
		if (flag >= 1) {
			fmt += ".";
			for (int i = 0; i < flag; i++) {
				fmt += "0";
			}
		}
		DecimalFormat df = new DecimalFormat(fmt);
		BigDecimal b = new BigDecimal(number).setScale(flag, BigDecimal.ROUND_DOWN);// 直接删除多余的小数位，如2.35会变成2.3
		String decimalStr = df.format(b.doubleValue()) + "";
		if (decimalStr.startsWith(".")) {
			decimalStr = "0" + decimalStr;
		}
		return decimalStr;
	}

	/**
	 * Bigdecimal类型 加法计算
	 * 
	 * @param bdm
	 * @return
	 */
	public static BigDecimal bigdecimalAdd(BigDecimal... bdm) {
		BigDecimal bigdecimal = new BigDecimal("0");
		for (int i = 0; i < bdm.length; i++) {
			if (bdm[i] != null) {
				bigdecimal = bigdecimal.add(bdm[i]);
			}
		}
		return bigdecimal;
	}

	public static void main(String[] args) {
	
	}

}

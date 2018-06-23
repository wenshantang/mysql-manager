package cn.aresoft.manager.web.util;

	import java.sql.Timestamp;
	import java.text.ParseException;
	import java.text.SimpleDateFormat;
	import java.util.Calendar;
	import java.util.Date;
	import java.util.Hashtable;

	public class DateHelper {
		public static final String CALENDER_DATE_FORMAT = "yyyy-MM-dd";
		public static final String CALENDER_MONTH_FORMAT = "yyyy-MM";
		public static final String CALENDER_YEAR_FORMAT = "yyyy";
		public static final String CALENDER_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
		public static final String CALENDER_TIME14_FORMAT = "yyyyMMddHHmmss";

		public static SimpleDateFormat sdfd2 = new SimpleDateFormat("yyyyMMdd");// add
																				// by
																				// shijx
																				// 时期格式

		public static SimpleDateFormat sdfs = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");// 时期格式
		public static SimpleDateFormat sdfs2 = new SimpleDateFormat(
				"yyyyMMddHHmmss");// 时期格式
		public static SimpleDateFormat sdfs3 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm");// 时期格式

		public static SimpleDateFormat sdfs_ch = new SimpleDateFormat(
				"yyyy年MM月dd日HH时mm分ss秒");// 时期格式

		public static SimpleDateFormat sdfd_ch = new SimpleDateFormat("yyyy年MM月dd日");// 时期格式

		public static SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");// 时期格式,时期格式
		public static SimpleDateFormat sdfd3 = new SimpleDateFormat("yy-MM-dd");// 时期格式,时期格式

		public static SimpleDateFormat sdfms = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");// 时期格式

		public static SimpleDateFormat sdfmss = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SS");// 时期格式

		public static SimpleDateFormat sdfdFold = new SimpleDateFormat("yyyy|MM|dd");// 时期格式,时期格式

		public static String sdfdFoldPattern = new String("yyyy|MM|dd");// 时期格式,时期格式

		/**
		 * 修改日期格式 query list 开通日期 20100415 改为 10-04-15
		 * 
		 * @param date
		 * @return
		 */
		public static String ChangDateFormat7(String dateString) {
			String back = dateString;
			try {

				Date date = sdfd2.parse(dateString);
				back = sdfd3.format(date);

			} catch (Exception ex) {
				// ex.printStackTrace();
				back = dateString;
			}
			return back;

		}

		/**
		 * 修改日期格式 华安 TA 服务中 20100415100141 改为 2010-04-15 10:01
		 * 
		 * @param date
		 * @return
		 */
		public static String ChangDateFormat6(String dateString) {
			String back = dateString;
			try {

				Date date = sdfs2.parse(dateString);
				back = sdfs3.format(date);

			} catch (Exception ex) {
				// ex.printStackTrace();
				back = dateString;
			}
			return back;

		}

		/**
		 * 修改日期格式 query list 开通日期 20100415 改为 2010-04-15
		 * 
		 * @param date
		 * @return
		 */
		public static String ChangDateFormat5(String dateString) {
			String back = dateString;
			try {

				Date date = sdfd2.parse(dateString);
				back = sdfd.format(date);

			} catch (Exception ex) {
				// ex.printStackTrace();
				back = dateString;
			}
			return back;

		}

		/**
		 * 修改日期格式 query list 开通日期 2010-04-15 改为 10-04-15
		 * 
		 * @param date
		 * @return
		 */
		public static String ChangDateFormat4(String dateString) {
			String back = dateString;
			try {

				Date date = sdfd.parse(dateString);
				back = sdfd3.format(date);

			} catch (Exception ex) {
				// ex.printStackTrace();
				back = dateString;
			}
			return back;

		}

		/**
		 * 修改日期格式 华安 TA 服务中 20100415 改为 2010年04月15日
		 * 
		 * @param date
		 * @return
		 */
		public static String ChangDateFormat3(String dateString) {
			String back = dateString;
			try {

				Date date = new Date();

				if (dateString.indexOf("-") == -1) {
					date = sdfd2.parse(dateString);
				} else {
					date = sdfd.parse(dateString);
				}

				back = sdfd_ch.format(date);

			} catch (Exception ex) {
				// ex.printStackTrace();
				back = dateString;
			}
			return back;

		}

		/**
		 * 修改日期格式 华安 TA 服务中 20100415100141 改为 2010-04-15 10:01:41
		 * 
		 * @param date
		 * @return
		 */
		public static String ChangDateFormat2(String dateString) {
			String back = dateString;
			try {

				Date date = sdfs2.parse(dateString);
				back = sdfs.format(date);

			} catch (Exception ex) {
				// ex.printStackTrace();
				back = dateString;
			}
			return back;

		}

		/**
		 * 修改日期格式 华安 TA 服务中 yyyy-MM-dd 改为 yyyyMMdd
		 * 
		 * @param date
		 * @return
		 */
		public static String ChangDateFormat(String dateString) {
			Date date = stringToDate(dateString);
			if (null != date) {
				return sdfd2.format(date);
			} else {
				return "";
			}
		}

		/**
		 * 10位日期转为date类型
		 * 
		 * @param date
		 * @return
		 */
		public static Date stringToDate(String date) {
			if (null != date && !"".equals(date)) {

				try {
					return sdfd.parse(date.trim());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		/**
		 * 8位日期转为date类型
		 * 
		 * @param date
		 * @return
		 */
		public static Date stringIn8ToDate(String date) {
			if (null != date && !"".equals(date)) {

				try {
					return sdfd2.parse(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		/**
		 * 根据传入的pattern格式化日期
		 * 
		 * @param date
		 * @param pattern
		 * @return
		 */
		public static String dateToStrByPattern(Date date, String pattern) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		}

		/**
		 * 获取跟当日间隔n天的日期
		 * 
		 * @param field
		 *            {@link Calendar}中 DATE MONTH YEAR...
		 * @param amount
		 *            偏离量,正数为当日之后，负数为当日之前
		 * @return
		 */
		public static Date getDateByInterval(int field, int amount) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(field, amount);
			return cal.getTime();
		}

		/**
		 * 日期类型格式化为10位字符串
		 * 
		 * @param date
		 * @return
		 */
		public static String getDate(java.util.Date date) {
			if (null != date) {
				return sdfd.format(date);
			} else {
				return "";
			}
		}
		
		public static String getDate(java.util.Date date,String format) {
			SimpleDateFormat format2 = new SimpleDateFormat(format);
			if (null != date) {
				return format2.format(date);
			} else {
				return "";
			}
		}

		/**
		 * 精确值秒的字符转为日期，格式:yyyy-MM-dd HH:mm:ss
		 * 
		 * @param date
		 * @return
		 */
		public static Date stringToTime(String date) {
			if (null != date && !"".equals(date)) {

				try {
					return sdfs.parse(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		/**
		 * 精确值毫秒的字符转为日期，格式:yyyy-MM-dd HH:mm:ss.SS
		 * 
		 * @param date
		 * @return
		 */
		public static Date stringToSecond(String date) {
			if (null != date && !"".equals(date)) {

				try {
					return sdfmss.parse(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		/**
		 * 验证起始日期是否早于截止日期
		 */
		public static String getDateRangeValidateMsg(Date start, Date end) {
			if (start.getTime() <= end.getTime()) {
				return "";
			} else {
				return "起始日期 必需早于 截止日期！";
			}
		}

		/**
		 * 验证起始日期是否早于截止日期
		 * 
		 * @param start
		 *            yyyy-MM-dd
		 * @param end
		 *            yyyy-MM-dd
		 * @return
		 */
		public static boolean judgeStartIsLessThanEnd(Date start, Date end) {
			start = stringToDate(getDate(start));
			end = stringToDate(getDate(end));
			if (start.getTime() <= end.getTime()) {
				return true;
			} else {
				return false;
			}
		}

		/**
		 * 返回指定格式的当前日期字符
		 * 
		 * @param pattern
		 * @return
		 */
		public static String getCurrTimeByPattern(String pattern) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(new Timestamp(System.currentTimeMillis()));
		}

		/**
		 * 获取当前年的第一天
		 * 
		 * @return
		 */
		public static Date getFirstDayOfYear() {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, 1);
			cal.set(Calendar.DAY_OF_YEAR, 1);
			return cal.getTime();
		}

		public static Date getBeforeDate(int beforeDays) {
			return getBeforeDate(new Date(), beforeDays);
		}

		public static Date getBeforeDate(Date date, int beforeDays) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_MONTH, beforeDays);
			return cal.getTime();
		}

		public static int getDateInterval(Date start, Date end) {
			Calendar startCal = Calendar.getInstance();
			Calendar endCal = Calendar.getInstance();
			startCal.setTime(start);
			endCal.setTime(end);

			// 设置时间为0时
			startCal.set(java.util.Calendar.HOUR_OF_DAY, 0);
			startCal.set(java.util.Calendar.MINUTE, 0);
			startCal.set(java.util.Calendar.SECOND, 0);
			endCal.set(java.util.Calendar.HOUR_OF_DAY, 0);
			endCal.set(java.util.Calendar.MINUTE, 0);
			endCal.set(java.util.Calendar.SECOND, 0);

			// 得到两个日期相差的天数
			int days = ((int) (endCal.getTime().getTime() / 1000) - (int) (startCal
					.getTime().getTime() / 1000)) / 3600 / 24;

			return days;
		}

		public static int getDayOfMonth(Date date) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			return c.get(Calendar.DAY_OF_MONTH);
		}

		public static boolean isBothMonth(Date d1, Date d2) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(d1);
			Calendar c2 = Calendar.getInstance();
			c2.setTime(d2);
			if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
					&& c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)) {
				return true;
			}
			return false;
		}

		public static boolean isBothDay(Date d1, Date d2) {
			if (isBothMonth(d1, d2)) {
				Calendar c1 = Calendar.getInstance();
				c1.setTime(d1);
				Calendar c2 = Calendar.getInstance();
				c2.setTime(d2);
				if (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)) {
					return true;
				}
			}
			return false;
		}

		public static Hashtable<String, Long> subtract(Date d1, Date d2) {
			long d3 = d2.getTime() - d1.getTime();
			long d = d3 / 24 / 60 / 60 / 1000;// 天
			long h = (d3 - d * 24 * 60 * 60 * 1000) / 60 / 60 / 1000;// 小时
			long m = (d3 - d * 24 * 60 * 60 * 1000 - h * 60 * 60 * 1000) / 60 / 1000;// 分
			long s = (d3 - d * 24 * 60 * 60 * 1000 - h * 60 * 60 * 1000 - m * 60 * 1000) / 1000;// 秒
			Hashtable<String, Long> map = new Hashtable<String, Long>();
			map.put("day", d);
			map.put("hour", h);
			map.put("minute", m);
			map.put("second", s);
			return map;
		}
	}


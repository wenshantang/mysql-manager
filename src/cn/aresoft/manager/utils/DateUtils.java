package cn.aresoft.manager.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.puff.framework.utils.StringUtil;

public class DateUtils {
	public static String format = "yyyy-MM-dd";
	public static String DATETIME_FORMAT = "yyyy/MM/dd HH:mm:ss";

	public static int maxYear = 50;

	public static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

	public static SimpleDateFormat sfh = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public static java.util.Date getWeekStart(java.util.Date curdate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(curdate);
		int index = cal.get(Calendar.DAY_OF_WEEK); // 今天是本周的第几天

		// 转成中国的习惯,如果是第一天,则转化为第七天(星期天外国为一周的第一天,而中国为每周的最后一天)

		if (index == 1)
			index = 8;

		cal.add(Calendar.DATE, -(index - 2));
		return cal.getTime();
	}

	public java.util.Date getWeekEnd(java.util.Date curdate) {
		java.util.Date sd = getWeekStart(curdate);
		return dateAdd(sd, 7);
	}

	public static java.util.Date dateAdd(java.util.Date curdate, int days) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(curdate);
		//int index = cal.get(Calendar.DAY_OF_WEEK); // 今天是本周的第几天
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public static java.util.Date hourAdd(java.util.Date curdate, int hour) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(curdate);
		//int index = cal.get(Calendar.DAY_OF_WEEK); // 今天是本周的第几天
		cal.add(Calendar.HOUR_OF_DAY, hour);
		return cal.getTime();
	}

	/**
	 * 获得上一天的日期
	 * 
	 * @return
	 */
	public java.util.Date getLastDate() {
		Date currentDate = new Date();
		return dateAdd(currentDate, -1);
	}

	public static String getThisWeekStart() {
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		Calendar cal = Calendar.getInstance();

		int index = cal.get(Calendar.DAY_OF_WEEK); // 今天是本周的第几天

		// 转成中国的习惯,如果是第一天,则转化为第七天(星期天外国为一周的第一天,而中国为每周的最后一天)

		if (index == 1)
			index = 8;

		cal.add(Calendar.DATE, -(index - 2));
		return (sdf.format(cal.getTime()));
	}

	public static String getThisWeekEnd() {
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		Calendar cal = Calendar.getInstance();

		int index = cal.get(Calendar.DAY_OF_WEEK); // 今天是本周的第几天

		// 转成中国的习惯,如果是第一天,则转化为第七天(星期天外国为一周的第一天,而中国为每周的最后一天)

		if (index == 1)
			index = 8;

		cal.add(Calendar.DATE, -(index - 2));
		cal.add(Calendar.DATE, 6);
		return (sdf.format(cal.getTime()));
	}

	public String date2str(java.util.Date date) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * @param date
	 * @return date 转换后的日期格式:'03-01-08'
	 */
	public static String date2str4excel(java.util.Date date) {
		String dateString = "";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		dateString = sf.format(date);
		String year = dateString.split("-")[0].substring(2, 4);
		String month = dateString.split("-")[1];
		String day = dateString.split("-")[2];
		dateString = day + "-" + month + "-" + year;
		return dateString;
	}

	/**
	 * 将FORM中未格式化的日期字符串转换成数据库所需日期字符串
	 * 
	 * @param unformatDateStr
	 *            前台页面FORM中未格式化的日期字符串 原数据样式: '010308'
	 * @return date 转换后的日期格式:'2008-01-03'
	 */
	public static Date str2date(String unformatDateStr) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = new String();
		String year = new String();
		String simpleYear = unformatDateStr.substring(4, unformatDateStr.length());
		if (new Integer(simpleYear).intValue() > maxYear)
			year = "19" + simpleYear;
		else
			year = "20" + simpleYear;
		String month = unformatDateStr.substring(0, 2);
		String day = unformatDateStr.substring(2, 4);
		dateStr = year + "-" + month + "-" + day;
		try {
			return sf.parse(dateStr);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return new Date();
	}

	/**
	 * 将FORM中未格式化的日期字符串转换成数据库所需日期字符串
	 * 
	 * @param unformatDateStr
	 *            前台页面FORM中未格式化的日期字符串 原数据样式: '010308'
	 * @return dateStr 转换后的日期格式:'2008-01-03'
	 */
	public String str2str(String unformatDateStr) {
		String dateStr = new String();
		String year = new String();
		String simpleYear = unformatDateStr.substring(4, unformatDateStr.length());
		if (new Integer(simpleYear).intValue() > maxYear)
			year = "19" + simpleYear;
		else
			year = "20" + simpleYear;
		String month = unformatDateStr.substring(0, 2);
		String day = unformatDateStr.substring(2, 4);
		dateStr = year + "-" + month + "-" + day;

		return dateStr;
	}

	/**
	 * 将FORM中未格式化的日期字符串转换成数据库所需日期字符串
	 * 
	 * @param unformatDateStr
	 *            前台页面FORM中未格式化的日期字符串 原数据样式: '200808'
	 * @return dateStr 转换后的日期格式:'2008-08'
	 */
	public String str2shortDateStr(String unformatDateStr) {
		String dateStr = new String();
		String year = unformatDateStr.substring(0, 4);
		String month = unformatDateStr.substring(5, 6);
		dateStr = year + "-" + month;
		return dateStr;
	}

	/**
	 * 将FORM中未格式化的日期字符串转换成数据库所需日期字符串
	 * 
	 * @param unformatDateStr
	 *            前台页面FORM中未格式化的日期字符串 原数据样式: '0108'
	 * @return dateStr 转换后的日期格式:'200801'
	 */
	public String str2MonthStr(String unformatDateStr) {
		String dateStr = new String();
		String year = new String();
		String simpleYear = unformatDateStr.substring(2, unformatDateStr.length());
		if (new Integer(simpleYear).intValue() > maxYear)
			year = "19" + simpleYear;
		else
			year = "20" + simpleYear;
		String month = unformatDateStr.substring(0, 2);
		dateStr = year + month;
		return dateStr;
	}

	/**
	 * 将日期转换为所需要的格式
	 */
	public String date2String4Spec(Date date) {
		String dateString = "";
		SimpleDateFormat sf = new SimpleDateFormat("MMddyy");
		dateString = sf.format(date);
		return dateString;
	}

	/**
	 * 将日期转换为所需要的格式
	 */
	public String date2String4yyyyMM(Date date) {
		String dateString = "";
		SimpleDateFormat sf = new SimpleDateFormat("yyMM");
		dateString = sf.format(date);
		return dateString;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public String date2String4Month(Date date) {
		String dateString = "";
		SimpleDateFormat sf = new SimpleDateFormat("yyMM");
		dateString = sf.format(date);
		return dateString;
	}

	/**
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date String2Date(String dateString) {
		ParsePosition pp = new ParsePosition(0);
		return sf.parse(dateString, pp);
	}

	/**
	 * 将日期转换为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String date2String(Date date) {
		sf.applyPattern("yyyy-MM-dd");
		String dateString = "";
		dateString = sf.format(date);
		return dateString;
	}

	/**
	 * 将日期转换为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String date2String(Date date, String pattern) {
		sf.applyPattern(pattern);
		String dateString = "";
		dateString = sf.format(date);
		return dateString;
	}

	/**
	 * Description: 日期增減月份
	 * 
	 * @param date
	 * @param month
	 *            往後3個月為3 往前3個月為-3
	 * @return
	 */
	public static Date addMonth(Date date, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);
		return calendar.getTime();
	}

	public static String date2MmDdYy(String dateStr) {
		String formatDateStr = "";
		String dateArray[] = dateStr.split("-");
		formatDateStr = dateArray[1] + dateArray[2] + dateArray[0].substring(2);
		return formatDateStr;
	}

	/**
	 * 将FORM中未格式化的日期字符串转换成数据库所需日期字符串
	 * 
	 * @param unformatDateStr
	 *            前台页面FORM中未格式化的日期字符串 原数据样式: '0308'
	 * @return date 转换后的日期格式:'200803'
	 */
	public String mmYy2YyyyMm(String mmYyStr) {
		String YyyyMmStr = new String();
		String year = new String();
		String simpleYear = mmYyStr.substring(0, 2);
		if (new Integer(simpleYear).intValue() > maxYear)
			year = "19" + simpleYear;
		else
			year = "20" + simpleYear;
		String month = mmYyStr.substring(2, mmYyStr.length());
		YyyyMmStr = year + month;
		return YyyyMmStr;
	}

	/**
	 * 将FORM中未格式化的日期字符串转换成数据库所需日期字符串
	 * 
	 * @param unformatDateStr
	 *            前台页面FORM中未格式化的日期字符串 原数据样式: '0803'
	 * @return date 转换后的日期格式:'200803'
	 */
	public static String yyMm2YyyyMm(String yyMm) {
		String YyyyMmStr = new String();
		String year = new String();
		String simpleYear = yyMm.substring(0, 2);
		if (new Integer(simpleYear).intValue() > maxYear)
			year = "19" + simpleYear;
		else
			year = "20" + simpleYear;
		String month = yyMm.substring(2, 4);
		YyyyMmStr = year + month;
		return YyyyMmStr;
	}

	/**
	 * 将FORM中未格式化的日期字符串转换成数据库所需日期字符串
	 * 
	 * @param unformatDateStr
	 *            前台页面FORM中未格式化的日期字符串 原数据样式: '2008-01-03'
	 * @return date 转换后的日期格式:'2008-01-03'
	 */
	public static Date string2date(String unformatDateStr) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sf.parse(unformatDateStr);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return new Date();
	}

	public static String timest_Str(Timestamp stamp) {
		String tsStr = "";

		try {
			if (stamp != null) {
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				tsStr = sdf.format(stamp);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return tsStr;
	}

	public static String getThisMonthStart() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	}

	public static String getThisMonthEnd() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	}

	public static String parseObjToTimeStr(Object obj) {
		Timestamp timestamp = null;
		String timestr = "";
		if (obj != null) {

			try {
				timestamp = Timestamp.valueOf(String.valueOf(obj));
				timestr = timest_Str(timestamp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return timestr;
	}

	public static String timest_Str2(Timestamp stamp) {
		String tsStr = "";

		try {
			if (stamp != null) {
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				tsStr = sdf.format(stamp);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return tsStr;
	}

	public static String GetNowDate() {
		String temp_str = "";
		Date dt = new Date();
		// 最后的aa表示“上午”或“下午” HH表示24小时制 如果换成hh表示12小时制
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		temp_str = sdf.format(dt);
		return temp_str;
	}

	public static String timest_Str3(Timestamp stamp) {
		String tsStr = "";

		try {
			if (stamp != null) {
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				tsStr = sdf.format(stamp);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return tsStr;
	}

	/**
	 * 将一个yyyy-MM-dd HH:mm:ss格式化的字符串时间转换为一个long值
	 * 
	 * @param formatDate
	 * @return
	 */
	public static long str2Long(String formatDate) {
		try {
			Date date = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			date = df.parse(formatDate);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static long getMonthSpace(Date date1, Date date2) {
		long result = 0;
		try {

			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long time = date2.getTime() - date1.getTime();

			result = time / 1000 / 60 / 60;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 日期格式转换字符串yyyy-MM-dd HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String timest_Str4(Date date) {
		String tsStr = "";

		try {
			if (date != null) {
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				tsStr = sdf.format(date);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return tsStr;
	}

	public static String timest_Str5(Date date) {
		String tsStr = "";

		try {
			if (date != null) {
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				tsStr = sdf.format(date);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return tsStr;
	}

	/**
	 * 将字符串时间格式化为yyyy-MM-dd HH:mm:ss日期时间
	 * 
	 * @param unformatDateStr
	 * @return
	 */
	public static Date string2date2(String unformatDateStr) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sf.parse(unformatDateStr);
		} catch (ParseException e) {

			// e.printStackTrace();
		}
		return new Date();
	}

	/**
	 * 将字符串按照指定格式转换为日期
	 * 
	 * @param unformatDateStr
	 * @param pattern
	 * @return
	 */
	public static Date converToDate(String unformatDateStr, String pattern) {
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		try {
			return sf.parse(unformatDateStr);
		} catch (ParseException e) {

			// e.printStackTrace();
		}
		return new Date();
	}

	/**
	 * format to yyyy/MM/dd HH:mm
	 * 
	 * @param date
	 * @return str
	 */
	public static String date2Str(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		try {
			return df.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return df.format(new Date());

	}

	/**
	 * format to yyyy/MM/dd
	 * 
	 * @param date
	 * @return
	 */
	public static String date2Str2(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		try {
			return df.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return df.format(new Date());

	}

	/**
	 * parse to yyyy/MM/dd HH:mm
	 * 
	 * @param str
	 * @return date
	 */
	public static Date str2Date(String str) {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		try {
			return df.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Date();

	}

	/**
	 * format to yyyy/MM/dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String date2Str3(Date date) {
		if (date == null) {
			return null;
		}
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			return df.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return df.format(new Date());

	}

	/**
	 * format to MM.dd.yyyy
	 * 
	 * @param date
	 * @return
	 */
	public static String date2Str4(Date date) {
		DateFormat df = new SimpleDateFormat("MM.dd.yyyy");
		try {
			return df.format(date);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return df.format(new Date());

	}

	/**
	 * format to yyyy-MM
	 * 
	 * @param date
	 * @return
	 */
	public static String date2Str5(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM");
		try {
			return df.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return df.format(new Date());
	}

	public static String str2StrDate(String dateStr) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = new Date(dateStr);
			return df.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return df.format(new Date());

	}

	/**
	 * 
	 * 
	 * @param date
	 *            日期为空，则为当前日期
	 * @param flag
	 *            【1：格式化为yyyyMMdd; 2:格式化为yyyyMMddHHmmssS; 3:格式化为：HHmmssS;4:格式化为yyyyMMddHHmmss;
	 *            <br/>5:格式化为：HHmmss;6:格式化为:yyyy-MM-dd HH:mm:ss,7:式化为:yyyy-MM-dd】默认为1
	 * @return
	 */
	public static String date2Str6(Date date, int flag) {
		try {
			if (date == null) {
				date = new Date();
			}
			DateFormat df;
			switch (flag) {
			case 1:
				df = new SimpleDateFormat("yyyyMMdd");
				break;
			case 2:
				df = new SimpleDateFormat("yyyyMMddHHmmssS");
				break;
			case 3:
				df = new SimpleDateFormat("HHmmssS");
				break;
			case 4:
				df=new SimpleDateFormat("yyyyMMddHHmmss");
				break;
			case 5:
				df=new SimpleDateFormat("HHmmss");
				break;
			case 6:
				df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				break;
			case 7:
				df=new SimpleDateFormat("yyyy-MM-dd");
				break;
			default:
				df = new SimpleDateFormat("yyyyMMdd");
				break;
			}
			return df.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/***
	 * 
	 * 将timeStamp格式化为字符串日期
	 * @param timeStamp 时间为null,返回空字符串
	 * @param flag  【1：格式化为yyyyMMdd; 2:格式化为yyyyMMddHHmmssS; 3:格式化为：HHmmssS;4:格式化为yyyyMMddHHmmss;
	 *            <br/>5:格式化为：HHmmss;6:格式化为:yyyy-MM-dd HH:mm:ss,7:式化为:yyyy-MM-dd】默认为1
	 * @return
	 */
	public static String timeStampToDateStr(Timestamp timeStamp,int flag){
		if(timeStamp!=null){
			try {
				DateFormat df;
				switch (flag) {
				case 1:
					df = new SimpleDateFormat("yyyyMMdd");
					break;
				case 2:
					df = new SimpleDateFormat("yyyyMMddHHmmssS");
					break;
				case 3:
					df = new SimpleDateFormat("HHmmssS");
					break;
				case 4:
					df=new SimpleDateFormat("yyyyMMddHHmmss");
					break;
				case 5:
					df=new SimpleDateFormat("HHmmss");
					break;
				case 6:
					df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					break;
				case 7:
					df=new SimpleDateFormat("yyyy-MM-dd");
					break;
				default:
					df = new SimpleDateFormat("yyyyMMdd");
					break;
				}
				Date date=new Date(timeStamp.getTime());
				return df.format(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "";
	}
	
	/**
	 * 日期添加天数后返回字符串类型日期
	 * @param date 日期
	 * @param days 天数
	 * @param flag  【1：格式化为yyyyMMdd; 2:yyyy-MM-dd】默认为1
	 * @return
	 */
	public static String dateAddStr(Date date,int days,int flag){
		Date d=dateAdd(date, days);
		if(flag==2){
			return date2Str6(d, 7);
		}else{
			return date2Str6(d, 1);
		}
		
	}
	
	  /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }    
      
	/** 
	*字符串的日期格式的计算 
	*/  
    public static int daysBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));     
    }
    
    /***
     * 判断当前时间内是否可以交易
     * <br/>晚上23:30 至 1:30 （购买、转让、充值、提现 四类交易动作在此时间段内不允许操作）
     * @return
     */
    public static boolean isTradeTime(){
		Calendar cal=Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute=cal.get(Calendar.MINUTE);
		if((hour>=23&&minute>=30)||(hour<1)||(hour==1&&minute<=30)){
			return false;
		}
		return true;
    }
	
    /**
     * 将yyyyMMdd字符串日期格式化为yyyy-MM-dd字符串日期
     * @param date
     * @return
     */
    public static String dateStr2Str(String date){
    	String result="";
    	if(StringUtil.notBlank(date)){
    		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");  
    		SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd"); 
    		try {
    			Date newDate=sdf.parse(date);
    			result=sdf2.format(newDate);
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
    	}
    	return result;
    }
    
    /**
     * 将yyyyMMddHHmmss字符串日期格式化为yyyy-MM-dd HH:mm:ss字符串日期
     * @param date
     * @return
     */
    public static String dateStr2Str2(String date){
    	String result="";
    	if(StringUtil.notBlank(date)){
    		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");  
    		SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    		try {
    			Date newDate=sdf.parse(date);
    			result=sdf2.format(newDate);
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
    	}
    	return result;
    }
}
package xyz.acproject.danmuji.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

public final class JodaTimeUtils {
	private static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	private static final String FORMAT_DATE ="yyyy-MM-dd";


	/**
	 * 获取10位时间戳
	 * @return
	 */
	public static long getTimestamp() {
		return System.currentTimeMillis()/1000;
	}

	/**
	 * 获取当前系统时间字符串
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentDateTimeString() {
	    DateTime dt = new DateTime();
	    String time = dt.toString(FORMAT_DATETIME);
	    return time;
	}
	/**
	 * 获取当前系统时间串
	 * @return yyyy-MM-dd HH:mm:ss.0
	 */
	public static Date getCurrentDateTime() {
	    DateTime dt = new DateTime();
	    String times = dt.toString(FORMAT_DATETIME);
	    Timestamp time = Timestamp.valueOf(times);
	    return time;
	}
	/**
	 * 获取当前日期字符串
	 * @return
	 */
	public static String getCurrentDateString() {
	    DateTime dt = new DateTime();
	    String date = dt.toString(FORMAT_DATE);
	    return date;
	}

	/**
	 * 获取系统当前时间按照指定格式返回
	 * @param pattern  yyyy/MM/dd hh:mm:a
	 * @return
	 */
	public static String getCurrentTimePattern(String pattern) {
	    DateTime dt = new DateTime();
	    String time = dt.toString(pattern);
	    return time;
	}
    /**
     * 获取当前毫秒
     * @return
     */
    public static long getcurrMills() {
    	Instant instant = Instant.now();
    	return instant.getMillis();
    }
    /**
     * 获取系统当前毫秒按指定日期字符串输入
     * @param pattern  yyyy-MM-dd
     * @return
     */
    public static long getcurrMillsPattern(String pattern) {
    	Instant instant = Instant.parse(pattern);
    	return instant.getMillis();
    }
    /**
	 * 毫秒转timeStamp
	 * @return yyyy-MM-dd HH:mm:ss.0
	 */
	public static Timestamp getCurrentTimestampMills(long Mills) {
	    Timestamp timestamp = new Timestamp(Mills);
	    return timestamp;
	}

	/**
	 * 获取指定时间
	 * @param year 年
	 * @param month 月
	 * @param day 天
	 * @param hour 小时
	 * @param minute 分钟
	 * @param seconds 秒
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getPointDateTime(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer seconds) {
	    DateTime dt = new DateTime(year, month, day, hour, minute, seconds);
	    String date = dt.toString(FORMAT_DATETIME);
	    return date;
	}


	public static Date getPointDateTime(Date date,Integer hour,Integer minute,Integer seconds) {
		DateTime dt = new DateTime(getYear(date), getMoth(date), getDay(date), hour, minute, seconds);
		return dt.toDate();
	}

	/**
	 *
	 * @param year 年
	 * @param month 月
	 * @param day 天
	 * @param hour 小时
	 * @param minute 分钟
	 * @param seconds 秒
	 * @param parrten 自定义格式
	 * @return parrten
	 */
	public static String getPointTimePattern(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer seconds, String parrten) {
	    DateTime dt = new DateTime(year, month, day, hour, minute, seconds);
	    String date = dt.toString(parrten);
	    return date;
	}

	/**
	 * 获取指定日期
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getPointDateString(Integer year, Integer month, Integer day) {
	    LocalDate dt = new LocalDate(year, month, day);
	    String date = dt.toString(FORMAT_DATE);
	    return date;
	}

	/**
	 * 获取指定日期
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date getPointDate(Integer year, Integer month, Integer day) {
		LocalDate dt = new LocalDate(year, month, day);
		return  dt.toDate();
	}

	/**
	 * 获取指定日期 返回指定格式
	 * @param year
	 * @param month
	 * @param day
	 * @param parrten
	 * @return
	 */
	public static String getPointTimeParrtenString(Integer year, Integer month, Integer day, String parrten) {
	    LocalDate dt = new LocalDate(year, month, day);
	    String date = dt.toString(parrten);
	    return date;
	}

	/**
	 * 获取当前是一周星期几 返回中文
	 * @return
	 */
	public static int getWeekByDateReturnNumber(Date date) {
		if(date==null) date = new Date();
		DateTime dts = new DateTime(date);
		return dts.getDayOfWeek();
	}

	/**
	 * 获取当前是一周星期几 返回数字
	 * @return
	 */
	public static String getCurrentWeek() {
		DateTime dts = new DateTime();
		String week = null;
		switch (dts.getDayOfWeek()) {
			case DateTimeConstants.SUNDAY:
				week = "星期日";
				break;

			case DateTimeConstants.MONDAY:
				week = "星期一";
				break;

			case DateTimeConstants.TUESDAY:
				week = "星期二";
				break;
			case DateTimeConstants.WEDNESDAY:
				week = "星期三";
				break;
			case DateTimeConstants.THURSDAY:
				week = "星期四";
				break;
			case DateTimeConstants.FRIDAY:
				week = "星期五";
				break;
			case DateTimeConstants.SATURDAY:
				week = "星期六";
			default:
				break;
		}
		return week;
	}


	/**
	 * 获取当前是一周星期几
	 * @return
	 */
	public static String formatWeek(Date date) {
		DateTime dts = new DateTime(date);
		String week = null;
		switch (dts.getDayOfWeek()) {
			case DateTimeConstants.SUNDAY:
				week = "星期日";
				break;

			case DateTimeConstants.MONDAY:
				week = "星期一";
				break;

			case DateTimeConstants.TUESDAY:
				week = "星期二";
				break;
			case DateTimeConstants.WEDNESDAY:
				week = "星期三";
				break;
			case DateTimeConstants.THURSDAY:
				week = "星期四";
				break;
			case DateTimeConstants.FRIDAY:
				week = "星期五";
				break;
			case DateTimeConstants.SATURDAY:
				week = "星期六";
			default:
				break;
		}
		return week;
	}

	/**
	 * 获取当前是一周星期几
	 * @return
	 */
	public static String formatWeek(Long mills) {
		DateTime dts = new DateTime(mills);
		String week = null;
		switch (dts.getDayOfWeek()) {
			case DateTimeConstants.SUNDAY:
				week = "星期日";
				break;

			case DateTimeConstants.MONDAY:
				week = "星期一";
				break;

			case DateTimeConstants.TUESDAY:
				week = "星期二";
				break;
			case DateTimeConstants.WEDNESDAY:
				week = "星期三";
				break;
			case DateTimeConstants.THURSDAY:
				week = "星期四";
				break;
			case DateTimeConstants.FRIDAY:
				week = "星期五";
				break;
			case DateTimeConstants.SATURDAY:
				week = "星期六";
			default:
				break;
		}
		return week;
	}


	/**
	 * 获取指定时间是一周的星期几
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getWeekPoint(Integer year, Integer month, Integer day) {
	    LocalDate dts = new LocalDate(year, month, day);
	    String week = null;
	    switch (dts.getDayOfWeek()) {
	    case DateTimeConstants.SUNDAY:
	        week = "星期日";
	        break;
	    case DateTimeConstants.MONDAY:
	        week = "星期一";
	        break;
	    case DateTimeConstants.TUESDAY:
	        week = "星期二";
	        break;
	    case DateTimeConstants.WEDNESDAY:
	        week = "星期三";
	        break;
	    case DateTimeConstants.THURSDAY:
	        week = "星期四";
	        break;
	    case DateTimeConstants.FRIDAY:
	        week = "星期五";
	        break;
	    case DateTimeConstants.SATURDAY:
	        week = "星期六";
	        break;

	    default:
	        break;
	    }
	    return week;
	}

	/**
	 * 按照时区转换时间
	 * @param date
	 * @param timeZone 时区
	 * @param parrten
	 * @return
	 */
	@Nullable
	public static String format(Date date, TimeZone timeZone, String parrten) {
		if (date == null) {
			return null;
		}
		DateTime dateTime = new DateTime(date);
		dateTime.withZone(DateTimeZone.forTimeZone(timeZone));
		return dateTime.toString(parrten);
	}
	/**
	 * 格式化日期
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	@Nullable
	public static String formatDateTime(Date date) {
	    if (date == null) {
	        return null;
	    }
	    DateTime dateTime = new DateTime(date);
	    return dateTime.toString(FORMAT_DATETIME);
	}

	/**
	 * 格式化日期
	 * @param date
	 * @return yyyy-MM-dd
	 */
	@Nullable
	public static String formatDate(Date date) {
		if (date == null) {
			return null;
		}
		DateTime dateTime = new DateTime(date);
		return dateTime.toString(FORMAT_DATE);
	}

	/**
	 * 格式化日期字符串
	 * @param date 日期
	 * @param pattern 日期格式
	 * @return
	 */
	@Nullable
	public static String format(Date date, String pattern) {
	    if (date == null) {
	        return null;
	    }
		DateTime dateTime = new DateTime(date);
		return dateTime.toString(pattern);
	}

	public static Integer formatToInt(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		DateTime dateTime = new DateTime(date);
		return Integer.parseInt(dateTime.toString(pattern));
	}

	/**
	 * 解析日期
	 * @param date 日期字符串
	 * @return
	 */
	@Nullable
	public static Date parse(String date) {
	    if (date == null) {
	        return null;
	    }
	    Date resultDate = DateTime.parse(date).toDate();
	    return resultDate;
	}

	public static Date parse(String date,String pattern){
		if(StringUtils.isBlank(pattern)){
			return parse(date);
		}
		DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(pattern);
		return DateTime.parse(date,dateTimeFormatter).toDate();
	}


	/**
	 * 解析日期 yyyy-MM-dd HH:mm:ss
	 * @param mills
	 * @return
	 */
	public static String format(Long mills, String pattern) {
	    String dateStr = "";
	    if (null == mills || mills.longValue() < 0) {
	        return dateStr;
	    }
	    try {
	        DateTime dateTime = new DateTime(mills);
	        dateStr = dateTime.toString(pattern);
	    } catch (Exception e) {
	        // ignore
	    }

	    return dateStr;
	}

	/**
	 * 解析日期 yyyy-MM-dd HH:mm:ss
	 * @param mills
	 * @return
	 */
	public static String formatDateTime(Long mills) {
	    String dateStr = "";
	    if (null == mills || mills.longValue() < 0) {
	        return dateStr;
	    }
	    try {
			DateTime dateTime = new DateTime(mills);
			dateStr = dateTime.toString(FORMAT_DATETIME);
	    } catch (Exception e) {
	        // ignore
	    }

	    return dateStr;
	}

	/**
	 * 解析日期 yyyy-MM-dd HH:mm:ss
	 * @param mills
	 * @return
	 */
	public static String formatDate(Long mills) {
		String dateStr = "";
		if (null == mills || mills.longValue() < 0) {
			return dateStr;
		}
		try {
			DateTime dateTime = new DateTime(mills);
			dateStr = dateTime.toString(FORMAT_DATE);
		} catch (Exception e) {
			// ignore
		}

		return dateStr;
	}


	/**
	 * 获取指定时间之后或者之前的某一天00:00:00 默认返回当天
	 * @param days
	 * @return
	 */
	public static Date day00(Integer days, String date, String zimeZone) throws Throwable {
	    DateTime dt;
	    TimeZone timeZone;
	    try {
	        if (StringUtils.isBlank(zimeZone)) {
	            timeZone = TimeZone.getDefault();
	        } else {
	            timeZone = TimeZone.getTimeZone(zimeZone);
	        }
	        if (StringUtils.isBlank(date)) {
	            dt = new DateTime().withZone(DateTimeZone.forTimeZone(timeZone)).toLocalDateTime().toDateTime();
	        } else {
	            dt = new DateTime(date).withZone(DateTimeZone.forTimeZone(timeZone)).toLocalDateTime().toDateTime();
	        }
	    } catch (Exception e) {
	        throw new Throwable(e);
	    }

	    DateTime y = dt.minusDays(days).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
	    return y.toDate();
	}

	/**
	 *获取指定时间之后或者之前的某一天23:59:59 默认返回当天
	 * @param days 偏移量
	 * @return
	 */
	public static Date day59(Integer days, String date, String zimeZone) throws Throwable {
	    DateTime dt;
	    TimeZone timeZone;
	    try {
	        if (StringUtils.isBlank(zimeZone)) {
	            timeZone = TimeZone.getDefault();
	        } else {
	            timeZone = TimeZone.getTimeZone(zimeZone);
	        }
	        if (StringUtils.isBlank(date)) {

	            dt = new DateTime().withZone(DateTimeZone.forTimeZone(timeZone)).toLocalDateTime().toDateTime();
	        } else {
	            dt = new DateTime(date).withZone(DateTimeZone.forTimeZone(timeZone)).toLocalDateTime().toDateTime();
	        }
	    } catch (Exception e) {
	        throw new Throwable(e);
	    }
	    DateTime y = dt.minusDays(days).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
	    return y.toDate();
	}

	/**
	 * 获取当天00:00:00 默认返回当天
	 * @param date
	 * @return
	 */
	public static Date day00(Date date) {
		DateTime dt = new DateTime(date);
		DateTime y = dt.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
		return y.toDate();
	}

	/**
	 *获取当天23:59:59 默认返回当天
	 * @param date 偏移量
	 * @return
	 */
	public static Date day59(Date date) {
		DateTime dt = new DateTime(date);
		DateTime y = dt.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
		return y.toDate();
	}

	/**
	 * 计算两个时间相差多少天
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Nullable
	public static Integer diffDay(Date startDate, Date endDate) {
	    if (startDate == null || endDate == null) {
	        return 0;
	    }
	    DateTime dt1 = new DateTime(startDate);
	    DateTime dt2 = new DateTime(endDate);
	    int day = Days.daysBetween(dt1, dt2).getDays();
	    return Math.abs(day);
	}

	public static long diffSecond(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return 0;
		}
		DateTime dt1 = new DateTime(startDate);
		DateTime dt2 = new DateTime(endDate);
		long seconds = Seconds.secondsBetween(dt1,dt2).getSeconds();
		return Math.abs(seconds);
	}


	/**
	 * 获取某月之前,之后某一个月最后一天,24:59:59
	 * @return
	 */
	public static Date monthLastDay(Date date, Integer month) {
	    DateTime dt1;
	    if (month == null) {
	        month = 0;
	    }
		if(month!=0){
			month = -month;
		}
	    if (date == null) {
	        dt1 = new DateTime().minusMonths(month);
	    } else {
	        dt1 = new DateTime(date).minusMonths(month);
	    }
	    DateTime lastDay = dt1.dayOfMonth().withMaximumValue().
	            withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
	    return lastDay.toDate();
	}

	/**
	 *获取某月月之前,之后某一个月第一天,00:00:00
	 * @return
	 */
	public static Date monthFirstDay(Date date, Integer month) {
	    DateTime dt1;
	    if (month == null) {
	        month = 0;
	    }
	    if(month!=0){
	    	month = -month;
		}
	    if (date == null) {
	        dt1 = new DateTime().minusMonths(month);
	    } else {
	        dt1 = new DateTime(date).minusMonths(month);
	    }
	    DateTime lastDay = dt1.dayOfMonth().withMinimumValue().
	            withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
	    return lastDay.toDate();
	}

	/**
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Date changeYear(Date date, int offset) {
		DateTime dt1;
		if(offset>=0) {
			if (date == null) {
				dt1 = new DateTime().plusYears(offset);
				return dt1.toDate();
			}
			dt1 = new DateTime(date).plusYears(offset);
		}else{
			if (date == null) {
				dt1 = new DateTime().minusYears(-1*offset);
				return dt1.toDate();
			}
			dt1 = new DateTime(date).minusYears(-1*offset);
		}
		return dt1.toDate();

	}


	/**
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Date changeMonth(Date date, int offset) {
		DateTime dt1;
		if(offset>=0) {
			if (date == null) {
				dt1 = new DateTime().plusMonths(offset);
				return dt1.toDate();
			}
			dt1 = new DateTime(date).plusMonths(offset);
		}else{
			if (date == null) {
				dt1 = new DateTime().minusMonths(-1*offset);
				return dt1.toDate();
			}
			dt1 = new DateTime(date).minusMonths(-1*offset);
		}
		return dt1.toDate();

	}

	/**
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Date changeDay(Date date, int offset) {
	    DateTime dt1;
	    if(offset>=0) {
			if (date == null) {
				dt1 = new DateTime().plusDays(offset);
				return dt1.toDate();
			}
			dt1 = new DateTime(date).plusDays(offset);
		}else{
			if (date == null) {
				dt1 = new DateTime().minusDays(-1*offset);
				return dt1.toDate();
			}
			dt1 = new DateTime(date).minusDays(-1*offset);
		}
	    return dt1.toDate();

	}

	/**
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Date changeHours(Date date, int offset) {
		DateTime dt1;
		if(offset>=0) {
			if (date == null) {
				dt1 = new DateTime().plusHours(offset);
				return dt1.toDate();
			}
			dt1 = new DateTime(date).plusHours(offset);
		}else{
			if (date == null) {
				dt1 = new DateTime().minusHours(-1*offset);
				return dt1.toDate();
			}
			dt1 = new DateTime(date).minusHours(-1*offset);
		}
		return dt1.toDate();

	}

	/**
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Date changeSeconds(Date date, int offset) {
		DateTime dt1;
		if(offset>=0) {
			if (date == null) {
				dt1 = new DateTime().plusSeconds(offset);
				return dt1.toDate();
			}
			dt1 = new DateTime(date).plusSeconds(offset);
		}else{
			if (date == null) {
				dt1 = new DateTime().minusSeconds(-1*offset);
				return dt1.toDate();
			}
			dt1 = new DateTime(date).minusSeconds(-1*offset);
		}
		return dt1.toDate();

	}

	/**
	 * 传入日期时间与当前系统日期时间的比较,
	 * 若日期相同，则根据时分秒来返回 ,
	 * 否则返回具体日期
	 * @return 日期或者 xx小时前||xx分钟前||xx秒前
	 */
	@Nullable
	public static String getNewUpdateDateString(Date now, Date createDate) {
	    if (now == null || createDate == null) {
	        return null;
	    }
	    Long time = (now.getTime() - createDate.getTime());
	    if (time > (24 * 60 * 60 * 1000)) {
	        return time / (24 * 60 * 60 * 1000) + "天前";
	    } else if (time > (60 * 60 * 1000)) {
	        return time / (60 * 60 * 1000) + "小时前";
	    } else if (time > (60 * 1000)) {
	        return time / (60 * 1000) + "分钟前";
	    } else if (time >= 1000) {
	        return time / 1000 + "秒前";
	    }
	    return "刚刚";
	}


	public static int getMonthDay(Date date){
		DateTime dateTime = new DateTime(date);
		return dateTime.dayOfMonth().getMaximumValue();
	}

	public static List<Date> getWeekDays(Date date){
		if(date==null){
			date = new Date();
		}
		List<Date> dates = new LinkedList<>();
		DateTime dateTime = new DateTime(date);
		for(int i=1;i<=7;i++) {
			dateTime = dateTime.withDayOfWeek(i).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);;
			dates.add(dateTime.toDate());
		}
		return dates;
	}

	public static List<Date> getMonthDays(Date date){
		if(date==null){
			date = new Date();
		}
		List<Date> dates = new LinkedList<>();
		DateTime dateTime = new DateTime(date);
		for(int i=1;i<=getMonthDay(date);i++) {
			dateTime = dateTime.withDayOfMonth(i).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);;
			dates.add(dateTime.toDate());
		}
		return dates;
	}

	public static Date getCurrentZero(){
		DateTime dateTime = new DateTime(new Date());
		dateTime = dateTime.withMillisOfDay(0);
		return dateTime.toDate();
	}
	/**
	* 从20210416起 弃用 请使用getYear(null)
	*/
	@Deprecated
	public static int getCurrentYear(){
		DateTime dateTime = new DateTime(new Date());
		return dateTime.getYear();
	}
	public static int getYear(Date date){
		if(date==null){
			date = new Date();
		}
		DateTime dateTime = new DateTime(date);
		return dateTime.getYear();
	}

	public static int getMoth(Date date){
		if(date==null){
			date = new Date();
		}

		DateTime dateTime = new DateTime(date);

		return dateTime.getMonthOfYear();
	}

	public static int getDay(Date date){
		if(date==null){
			date = new Date();
		}
		DateTime dateTime = new DateTime(date);
		return dateTime.getDayOfMonth();
	}

	public static int getHour(Date date){
		if(date==null){
			date = new Date();
		}
		DateTime dateTime = new DateTime(date);
		return dateTime.getHourOfDay();
	}


	public static int getMinute(Date date){
		if(date==null){
			date = new Date();
		}
		DateTime dateTime = new DateTime(date);
		return dateTime.getMinuteOfHour();
	}

	public static Date getZero(Date date){
		DateTime dateTime = new DateTime(date);
		dateTime = dateTime.withMillisOfDay(0);
		return dateTime.toDate();
	}

	public static boolean compareNow(Date sourceTime){
		DateTime sourceDate = new DateTime(sourceTime);
		return sourceDate.isBeforeNow();
	}

	//几个月一个季度 2 3 4 6
	public static Date[] getQuarterlys(Date date,Integer quarterlyNum){
		Date[] qs = new Date[2];
		int month = getMoth(date);
		if(quarterlyNum==2){
			if((month&2)==1){
				qs[0]=monthFirstDay(date,0);
				qs[1]=monthLastDay(date,1);
			}else{
				qs[0]=monthFirstDay(date,-1);
				qs[1]=monthLastDay(date,0);
			}
		}else if(quarterlyNum==3){
			switch (month){
				case 1:
					qs[0]=monthFirstDay(date,0);
					qs[1]=monthLastDay(date,2);
					break;
				case 2:
					qs[0]=monthFirstDay(date,-1);
					qs[1]=monthLastDay(date,1);
					break;
				case 3:
					qs[0]=monthFirstDay(date,-2);
					qs[1]=monthLastDay(date,0);
					break;
				case 4:
					qs[0]=monthFirstDay(date,0);
					qs[1]=monthLastDay(date,2);
					break;
				case 5:
					qs[0]=monthFirstDay(date,-1);
					qs[1]=monthLastDay(date,1);
					break;
				case 6:
					qs[0]=monthFirstDay(date,-2);
					qs[1]=monthLastDay(date,0);
					break;
				case 7:
					qs[0]=monthFirstDay(date,0);
					qs[1]=monthLastDay(date,2);
					break;
				case 8:
					qs[0]=monthFirstDay(date,-1);
					qs[1]=monthLastDay(date,1);
					break;
				case 9:
					qs[0]=monthFirstDay(date,-2);
					qs[1]=monthLastDay(date,0);
					break;
				case 10:
					qs[0]=monthFirstDay(date,-0);
					qs[1]=monthLastDay(date,2);
					break;
				case 11:
					qs[0]=monthFirstDay(date,-1);
					qs[1]=monthLastDay(date,1);
					break;
				case 12:
					qs[0]=monthFirstDay(date,-2);
					qs[1]=monthLastDay(date,0);
					break;
			}
		}else if(quarterlyNum==4){
			switch (month){
				case 1:
					qs[0]=monthFirstDay(date,0);
					qs[1]=monthLastDay(date,3);
					break;
				case 2:
					qs[0]=monthFirstDay(date,-1);
					qs[1]=monthLastDay(date,2);
					break;
				case 3:
					qs[0]=monthFirstDay(date,-2);
					qs[1]=monthLastDay(date,1);
					break;
				case 4:
					qs[0]=monthFirstDay(date,-3);
					qs[1]=monthLastDay(date,0);
					break;
				case 5:
					qs[0]=monthFirstDay(date,0);
					qs[1]=monthLastDay(date,3);
					break;
				case 6:
					qs[0]=monthFirstDay(date,-1);
					qs[1]=monthLastDay(date,2);
					break;
				case 7:
					qs[0]=monthFirstDay(date,-2);
					qs[1]=monthLastDay(date,1);
					break;
				case 8:
					qs[0]=monthFirstDay(date,-3);
					qs[1]=monthLastDay(date,0);
					break;
				case 9:
					qs[0]=monthFirstDay(date,0);
					qs[1]=monthLastDay(date,3);
					break;
				case 10:
					qs[0]=monthFirstDay(date,-1);
					qs[1]=monthLastDay(date,2);
					break;
				case 11:
					qs[0]=monthFirstDay(date,-2);
					qs[1]=monthLastDay(date,1);
					break;
				case 12:
					qs[0]=monthFirstDay(date,-3);
					qs[1]=monthLastDay(date,0);
					break;
			}
		}else if(quarterlyNum==6){
			switch (month){
				case 1:
					qs[0]=monthFirstDay(date,0);
					qs[1]=monthLastDay(date,5);
					break;
				case 2:
					qs[0]=monthFirstDay(date,-1);
					qs[1]=monthLastDay(date,4);
					break;
				case 3:
					qs[0]=monthFirstDay(date,-2);
					qs[1]=monthLastDay(date,3);
					break;
				case 4:
					qs[0]=monthFirstDay(date,-3);
					qs[1]=monthLastDay(date,2);
					break;
				case 5:
					qs[0]=monthFirstDay(date,-4);
					qs[1]=monthLastDay(date,1);
					break;
				case 6:
					qs[0]=monthFirstDay(date,-5);
					qs[1]=monthLastDay(date,0);
					break;
				case 7:
					qs[0]=monthFirstDay(date,0);
					qs[1]=monthLastDay(date,5);
					break;
				case 8:
					qs[0]=monthFirstDay(date,-1);
					qs[1]=monthLastDay(date,4);
					break;
				case 9:
					qs[0]=monthFirstDay(date,-2);
					qs[1]=monthLastDay(date,3);
					break;
				case 10:
					qs[0]=monthFirstDay(date,-3);
					qs[1]=monthLastDay(date,2);
					break;
				case 11:
					qs[0]=monthFirstDay(date,-4);
					qs[1]=monthLastDay(date,1);
					break;
				case 12:
					qs[0]=monthFirstDay(date,-5);
					qs[1]=monthLastDay(date,0);
					break;
			}
		}
		return qs;
	}
	public static String formatSecond(int second) {
		Seconds seconds = Seconds.seconds(second);
		Period period = new Period(seconds);
		PeriodFormatter periodFormatter = new PeriodFormatterBuilder()
				.appendDays()
				.appendSuffix("天")
				.appendHours()
				.appendSuffix("时")
				.appendMinutes()
				.appendSuffix("分")
				.appendSeconds()
				.appendSuffix("秒").toFormatter();
		return periodFormatter.print(period.normalizedStandard());
	}

}

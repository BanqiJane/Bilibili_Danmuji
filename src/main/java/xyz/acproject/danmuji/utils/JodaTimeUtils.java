package xyz.acproject.danmuji.utils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.springframework.lang.Nullable;

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
	public static String getPointDate(Integer year, Integer month, Integer day) {
	    LocalDate dt = new LocalDate(year, month, day);
	    String date = dt.toString(FORMAT_DATE);
	    return date;
	}

	/**
	 * 获取指定日期 返回指定格式
	 * @param year
	 * @param month
	 * @param day
	 * @param parrten
	 * @return
	 */
	public static String getPointTimeParrten(Integer year, Integer month, Integer day, String parrten) {
	    LocalDate dt = new LocalDate(year, month, day);
	    String date = dt.toString(parrten);
	    return date;
	}

	/**
	 * 获取当前是一周星期几
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
		dateTime.toString(parrten);
		dateTime.withZone(DateTimeZone.forTimeZone(timeZone));
		return dateTime.toString();
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
	 * 计算两个时间相差多少天
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Nullable
	public static Integer diffDay(Date startDate, Date endDate) {
	    if (startDate == null || endDate == null) {
	        return null;
	    }
	    DateTime dt1 = new DateTime(startDate);
	    DateTime dt2 = new DateTime(endDate);
	    int day = Days.daysBetween(dt1, dt2).getDays();
	    return Math.abs(day);
	}

	/**
	 * 获取某月之前,之后某一个月最后一天,24:59:59
	 * @return
	 */
	public static Date lastDay(Date date, Integer month) {
	    DateTime dt1;
	    if (month == null) {
	        month = 0;
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
	public static Date firstDay(Date date, Integer month) {
	    DateTime dt1;
	    if (month == null) {
	        month = 0;
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
				dt1 = new DateTime().minusDays(offset);
				return dt1.toDate();
			}
			dt1 = new DateTime(date).minusDays(offset);
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
				dt1 = new DateTime().minusMonths(offset);
				return dt1.toDate();
			}
			dt1 = new DateTime(date).minusMonths(offset);
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
				dt1 = new DateTime().minusSeconds(offset);
				return dt1.toDate();
			}
			dt1 = new DateTime(date).minusSeconds(offset);
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

}

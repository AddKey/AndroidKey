package com.addkey.keylibrary.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
	
	public static final SimpleDateFormat format_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat format_yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final SimpleDateFormat format_yyyy_MM_dd_HH_mm_ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat format_yyyy_MM_dd_HH_mm_ss_SSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	public static final SimpleDateFormat format_MMddHHmmssSSS = new SimpleDateFormat("MMddHHmmssSSS");
	public static final SimpleDateFormat format_yyyMMddHHmmssSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	public static final String yyyyMMdd = "yyyyMMdd";
	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String MMddHHmmssSSS = "MMddHHmmssSSS";
	public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
	
	/**
	 * 将日期类型转成特定格式字符串
	 * @param formatStr 格式化类型
	 * @return
	 */
	public static String format(String formatStr){
		return format(new Date(),formatStr);
	}
	/**
	 * 将日期类型转成特定格式字符串
	 * @param formatStr 格式化类型
	 * @param field 日历类型
	 * @param offset 偏移量
	 * @return
	 */
	public static String format(String formatStr, int field, int offset){
		return format(new Date(), formatStr, field,offset);
	}	
	/**
	 * 将日期类型转成特定格式字符串
	 * @param dateTime 传入的时间
	 * @param formatStr 格式化类型
	 * @return
	 */
	public static String format(Date dateTime, String formatStr){
		return format(dateTime, formatStr,-1, 0);
	}
	/**
	 * 将日期类型转成特定格式字符串
	 * @param timeMillis 传入的时间毫秒
	 * @param formatStr 格式化类型
	 * @return
	 */
	public static String format(long timeMillis, String formatStr){
		Date dateTime = new Date(timeMillis);
		return format(dateTime, formatStr,-1, 0);
	}

	/**
	 * 将日期类型转成特定格式字符串
	 * @param dateTime 传入的时间
	 * @param formatStr 格式化类型
	 * @param field 日历类型
	 * @param offset 偏移量
	 * @return
	 */
	public static String format(Date dateTime, String formatStr, int field, int offset){
        if(dateTime == null){
            return "";
        }
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTime);
		if(field != -1){
			cal.add(field,offset);
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
		return dateFormat.format(cal.getTime());
	}
	/**
	 * 将字符串类型转成日期类型
	 * @param dateStr 传入的时间
	 * @param formatStr 格式化类型
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String dateStr, String formatStr) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
		return dateFormat.parse(dateStr);
	}
	/**
	 * 日的偏移运算
	 * @param day 日偏移量
	 * @return
	 */
	public static Date offsetDay(int day){
		return offsetTime(new Date(), 0, 0, day, 0, 0, 0);
	}	
	/**
	 * 周的偏移运算
	 * @param week 周偏移量
	 * @return
	 */
	public static Date offsetWeek(int week){
		return offsetTime(new Date(), 0, 0, 7*week, 0, 0, 0);
	}		
	/**
	 * 月的偏移运算
	 * @param month 月偏移量
	 * @return
	 */
	public static Date offsetMonth(int month){
		return offsetTime(new Date(), 0, month, 0, 0, 0, 0);
	}
    /**
     * 季的偏移运算
     * @param quarter 季偏移量
     * @return
     */
    public static Date offsetQuarter(int quarter){
        return offsetTime(new Date(), 0, 3*quarter, 0, 0, 0, 0);
    }
	/**
	 * 年的偏移运算
	 * @param year 年偏移量
	 * @return
	 */
	public static Date offsetYear(int year){
		return offsetTime(new Date(), year, 0, 0, 0, 0, 0);
	}
	/**
	 * 日的偏移运算
	 * @param dateTime 传入的时间
	 * @param day 日偏移量
	 * @return
	 */
	public static Date offsetDay(Date dateTime, int day){
		return offsetTime(dateTime, 0, 0, day, 0, 0, 0);
	}	
	/**
	 * 周的偏移运算
	 * @param dateTime 传入的时间
	 * @param week 周偏移量
	 * @return
	 */
	public static Date offsetWeek(Date dateTime, int week){
		return offsetTime(dateTime, 0, 0, week*7, 0, 0, 0);
	}
	/**
	 * 月的偏移运算
	 * @param dateTime 传入的时间
	 * @param month 月偏移量
	 * @return
	 */
	public static Date offsetMonth(Date dateTime, int month){
		return offsetTime(dateTime, 0, month, 0, 0, 0, 0);
	}
	/**
	 * 季的偏移运算
	 * @param dateTime 传入的时间
	 * @param quarter 季偏移量
	 * @return
	 */
	public static Date offsetQuarter(Date dateTime, int quarter){
		return offsetTime(dateTime, 0, 3*quarter, 0, 0, 0, 0);
	}
    /**
     * 年的偏移运算
     * @param year 年偏移量
     * @return
     */
    public static Date offsetYear(Date dateTime, int year){
        return offsetTime(dateTime, year, 0, 0, 0, 0, 0);
    }
	/**
	 * 秒的偏移运算
	 * @param second 秒偏移量
	 * @return
	 */
	public static Date offsetTime(int second){
		return offsetTime(new Date(), 0, 0, 0, 0, 0, second);
	}
	/**
	 * 分秒的偏移运算
	 * @param minute 分偏移量
	 * @param second 秒偏移量
	 * @return
	 */
	public static Date offsetTime(int minute, int second){
		return offsetTime(new Date(), 0, 0, 0, 0, minute, second);
	}
	/**
	 * 时分秒的偏移运算
	 * @param hour 时偏移量
	 * @param minute 分偏移量
	 * @param second 秒偏移量
	 * @return
	 */
	public static Date offsetTime(int hour, int minute, int second){
		return offsetTime(new Date(), 0, 0, 0, hour, minute, second);
	}
	/**
	 * 日时分秒的偏移运算
	 * @param day 日偏移量
	 * @param hour 时偏移量
	 * @param minute 分偏移量
	 * @param second 秒偏移量
	 * @return
	 */
	public static Date offsetTime(int day, int hour, int minute, int second){
		return offsetTime(new Date(), 0, 0, day, hour, minute, second);
	}
	/**
	 * 月日时分秒的偏移运算
	 * @param month 月偏移量
	 * @param day 日偏移量
	 * @param hour 时偏移量
	 * @param minute 分偏移量
	 * @param second 秒偏移量
	 * @return
	 */
	public static Date offsetTime(int month, int day, int hour, int minute, int second){
		return offsetTime(new Date(), 0, month, day, hour, minute, second);
	}
	/**
	 * 年月日时分秒的偏移运算
	 * @param year 年偏移量
	 * @param month 月偏移量
	 * @param day 日偏移量
	 * @param hour 时偏移量
	 * @param minute 分偏移量
	 * @param second 秒偏移量
	 * @return
	 */
	public static Date offsetTime(int year, int month, int day, int hour, int minute, int second){
		return offsetTime(new Date(), year, month, day, hour, minute, second);
	}
	/**
	 * 时间的偏移运算函数
	 * @param dateTime 传入的时间
	 * @param second 秒偏移量
	 * @return 偏移后的时间
	 */
	public static Date offsetTime(Date dateTime, int second){
		return offsetTime(dateTime, 0, 0, 0, 0, 0, second);
	}
	/**
	 * 时间的偏移运算函数
	 * @param dateTime 传入的时间
	 * @param minute 分偏移量
	 * @param second 秒偏移量
	 * @return 偏移后的时间
	 */
	public static Date offsetTime(Date dateTime, int minute, int second){
		return offsetTime(dateTime, 0, 0, 0, 0, minute, second);
	}
	/**
	 * 时间的偏移运算函数
	 * @param dateTime 传入的时间
	 * @param hour 时偏移量
	 * @param minute 分偏移量
	 * @param second 秒偏移量
	 * @return 偏移后的时间
	 */
	public static Date offsetTime(Date dateTime, int hour, int minute, int second){
		return offsetTime(dateTime, 0, 0, 0, hour, minute, second);
	}
	/**
	 * 时间的偏移运算函数
	 * @param dateTime 传入的时间
	 * @param day 日偏移量
	 * @param hour 时偏移量
	 * @param minute 分偏移量
	 * @param second 秒偏移量
	 * @return 偏移后的时间
	 */
	public static Date offsetTime(Date dateTime, int day, int hour, int minute, int second){
		return offsetTime(dateTime, 0, 0, day, hour, minute, second);
	}
	/**
	 * 时间的偏移运算函数
	 * @param dateTime 传入的时间
	 * @param month 月偏移量
	 * @param day 日偏移量
	 * @param hour 时偏移量
	 * @param minute 分偏移量
	 * @param second 秒偏移量
	 * @return 偏移后的时间
	 */
	public static Date offsetTime(Date dateTime, int month, int day, int hour, int minute, int second){
		return offsetTime(dateTime, 0, month, day, hour, minute, second);
	}
	/**
	 * 时间的偏移运算函数
	 * @param dateTime 传入的时间
	 * @param year 年偏移量
	 * @param month 月偏移量
	 * @param day 日偏移量
	 * @param hour 时偏移量
	 * @param minute 分偏移量
	 * @param second 秒偏移量
	 * @return 偏移后的时间
	 */
	public static Date offsetTime(Date dateTime, int year, int month, int day, int hour, int minute, int second){
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTime);
		cal.add(Calendar.YEAR,year);
		cal.add(Calendar.MONTH,month);
		cal.add(Calendar.DATE,day);
		cal.add(Calendar.HOUR_OF_DAY,hour);
		cal.add(Calendar.MINUTE,minute);
		cal.add(Calendar.SECOND,second);
		return cal.getTime();
	}
	
	public static Date specifyDay(Date dateTime, int day){
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTime);
		cal.set(Calendar.DATE,day);
		return cal.getTime();
	} 
	public static Date specifyTime(int second){
		return specifyTime(new Date(), -1, -1, -1, -1, -1, second);
	}
	public static Date specifyTime(int minute, int second){
		return specifyTime(new Date(), -1, -1, -1, -1, minute, second);
	}
	public static Date specifyTime(int hour, int minute, int second){
		return specifyTime(new Date(), -1, -1, -1, hour, minute, second);
	}
	public static Date specifyTime(int day, int hour, int minute, int second){
		return specifyTime(new Date(), -1, -1, day, hour, minute, second);
	}
	public static Date specifyTime(int month, int day, int hour, int minute, int second){
		return specifyTime(new Date(), -1, month, day, hour, minute, second);
	}
	public static Date specifyTime(int year, int month, int day, int hour, int minute, int second){
		return specifyTime(new Date(), year, month, day, hour, minute, second);
	}
	public static Date specifyTime(Date dateTime, int second){
		return specifyTime(dateTime, -1, -1, -1, -1, -1, second);
	}
	public static Date specifyTime(Date dateTime, int minute, int second){
		return specifyTime(dateTime, -1, -1, -1, -1, minute, second);
	}
	public static Date specifyTime(Date dateTime, int hour, int minute, int second){
		return specifyTime(dateTime, -1, -1, -1, hour, minute, second);
	}
	public static Date specifyTime(Date dateTime, int day, int hour, int minute, int second){
		return specifyTime(dateTime, -1, -1, day, hour, minute, second);
	}
	public static Date specifyTime(Date dateTime, int month, int day, int hour, int minute, int second){
		return specifyTime(dateTime, -1, month, day, hour, minute, second);
	}
	/**根据传入的年月日时分秒，指定时间
	 * @param dateTime	传入的时间
	 * @param year	设置的年
	 * @param month	设置的月
	 * @param day	设置的天
	 * @param hour	设置的时
	 * @param minute	设置的分
	 * @param second	设置的秒
	 * @return	设置后的时间
	 */
	public static Date specifyTime(Date dateTime, int year, int month, int day, int hour, int minute, int second){
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTime);
		if(year != -1){
			cal.set(Calendar.YEAR,year);
		}
		if(month != -1){
			cal.set(Calendar.MONTH,month);
		}
		if(day != -1){
			cal.set(Calendar.DATE,day);
		}
		if(hour != -1){
			cal.set(Calendar.HOUR_OF_DAY,hour);
		}
		if(minute != -1){
			cal.set(Calendar.MINUTE,minute);
		}
		if(second != -1){
			cal.set(Calendar.SECOND,second);
		}
		return cal.getTime();
	}
	/**
	 * 获得指定日期的天开始时间
	 * @param date
	 * @return
	 */
	public static Date getStartTimeOfDay(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		date = (Date) cal.getTime();
		return date;
	}
	
	/**
	 * 获得指定日期的天结束时间
	 * @param date
	 * @return
	 */
	public static Date getEndTimeOfDay(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY,23);
		cal.set(Calendar.MINUTE,59);
		cal.set(Calendar.SECOND,59);
		date = (Date) cal.getTime();
		return date;
	}

    /**
     * 获得指定日期的周开始时间
     * @param date
     * @return
     */
    public static Date getStartTimeOfWeek(Date date){
        Calendar cal= Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        return cal.getTime();
    }

    /**
     * 获得指定日期的周结束时间
     * @param date
     * @return
     */
    public static Date getEndTimeOfWeek(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND,59);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        date = (Date) cal.getTime();
        return date;
    }

    /**
	 * 根据传入的时间，获得月开始时间
	 * @param date
	 * @return
	 */
	public static Date getStartTimeOfMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		date = (Date) cal.getTime();
		return date;
	}
	
	/**
	 * 根据传入的时间，获得月结束时间
	 * @param date
	 * @return
	 */
	public static Date getEndTimeOfMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DATE, day);
		cal.set(Calendar.HOUR_OF_DAY,23);
		cal.set(Calendar.MINUTE,59);
		cal.set(Calendar.SECOND,59);
		date = (Date) cal.getTime();
		return date;
	}
	/**
	 * 根据传入的年月字符串，获得月开始时间
	 * @param dateStr
	 * @param formatStr
	 * @return
	 * @throws ParseException
	 */
	public static Date getStartTimeOfMonth(String dateStr, String formatStr) throws ParseException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
		Date date = dateFormat.parse(dateStr);
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		date = (Date) cal.getTime();
		return date;
	}
	
	/**根据传入的年月字符串，获得月结束时间
	 * @param dateStr
	 * @param formatStr
	 * @return
	 * @throws ParseException
	 */
	public static Date getEndTimeOfMonth(String dateStr, String formatStr) throws ParseException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
		Date date = dateFormat.parse(dateStr);
		cal.setTime(date);
		int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DATE, day);
		cal.set(Calendar.HOUR_OF_DAY,23);
		cal.set(Calendar.MINUTE,59);
		cal.set(Calendar.SECOND,59);
		date = (Date) cal.getTime();
		return date;
	}
	/**
	 * 根据传入的时间，获得月开始时间
	 * @param date
	 * @return
	 */
	public static Date getStartTimeOfYear(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		date = (Date) cal.getTime();
		return date;
	}
	
	/**
	 * 根据传入的时间，获得月结束时间
	 * @param date
	 * @return
	 */
	public static Date getEndTimeOfYear(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.MONTH, 11);
		cal.set(Calendar.DATE, day);
		cal.set(Calendar.HOUR_OF_DAY,23);
		cal.set(Calendar.MINUTE,59);
		cal.set(Calendar.SECOND,59);
		date = (Date) cal.getTime();
		return date;
	}	
	/**
	 * 返回两个日期相差的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public long getDistDates(Date startDate, Date endDate){
		long totalDate = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		long timestart = cal.getTimeInMillis();
		cal.setTime(endDate);
		long timeend = cal.getTimeInMillis();
		totalDate = Math.abs((timeend - timestart))/(1000*60*60*24);
		return totalDate;
	}

    public static int getWeekOfYear(Date dateTime){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(dateTime);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        return week;
    }

    public static int getMonthByWeek(int week){
        Calendar cal= Calendar.getInstance();
        cal.set(Calendar.WEEK_OF_YEAR, week);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int month = cal.get(Calendar.MONTH)+1;
        return month;
    }

    public static int getQuarterByMonth(int month){
        int quarter = 0;
        if(month>=1 && month<=3){
            quarter = 1;
        }
        if(month>=4 && month<=6){
            quarter = 2;
        }
        if(month>=7 && month<=9){
            quarter = 3;
        }
        if(month>=10 && month<=12){
            quarter = 4;
        }
        return quarter;
    }

    public static int getQuarterOfYear(Date dateTime){
        Calendar cal= Calendar.getInstance();
        cal.setTime(dateTime);
        int month = cal.get(Calendar.MONTH) + 1;
        int quarter = 0;
        if(month>=1 && month<=3){
            quarter = 1;
        }
        if(month>=4 && month<=6){
            quarter = 2;
        }
        if(month>=7 && month<=9){
            quarter = 3;
        }
        if(month>=10 && month<=12){
            quarter = 4;
        }
        return quarter;
    }
    public static int getYear(Date dateTime){
        Calendar cal= Calendar.getInstance();
        cal.setTime(dateTime);
        return cal.get(Calendar.YEAR);
    }

    public static int getQuarterByWeek(int week){
        return getQuarterByMonth(getMonthByWeek(week));
    }

	public static void main(String[] args) throws ParseException {
		System.out.println(getWeekOfYear(new Date()));
		System.out.println(getMonthByWeek(38));
		System.out.println(getStartTimeOfWeek(new Date()));
		System.out.println(getEndTimeOfWeek(new Date()));
		System.out.println(getQuarterOfYear(new Date()));
		System.out.println(getYear(new Date()));
		System.out.println(format(parse("2014-01-01", "yyyy-MM-dd"), "yyyy-w"));
        System.out.println(parse("2014-10-01","yyyy-MM-dd").getTime());
        System.out.println(format("yyyy") );
        System.out.println(format("yyyy-M") );
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date = new Date(1438591770000L);
		System.out.println(sdf.format(date));

	}
}

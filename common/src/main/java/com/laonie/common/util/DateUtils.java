package com.laonie.common.util;

import android.support.annotation.NonNull;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_FORMAT1 = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT2 = "MM/dd/yyyy";
    public static final String DATE_FORMAT3 = "yyyy年MM月dd日";
    public static final String DATE_FORMAT4 = "yyyyMMdd";
    public static final String DATE_FORMAT5 = "yyyy/MM/dd";
    public static final String DATE_FORMAT6 = "yyyy年MM月";
    public static final String DATE_FORMAT7 = "yyyy-MM";
    public static final String DATE_FORMAT8 = "yyyy年MM月";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String TIME_NO_FORMAT = "HHmmss";
    public static final String TIME_FORMAT2 = "HH:mm";
    public static final String TIME_FORMAT3 = "hh:mm";
    public static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";
    public static final String MILLI_TIMESTAMP_FORMAT = "yyMMddHHmmssSSS";//精确到毫秒时间戳
    public static final String ZEROTIME_FORMAT = "yyyy-MM-dd 00:00:00";    //当天零点
    public static final String ENDTIME_FORMAT = "yyyy-MM-dd 23:59:59";    //当天的最后一秒
    public static final String M2_D2_H2_M2_FORMAT = "MM-dd HH:mm";
    public static final String M2_D2_FORMAT = "MM-dd";
    public static final String YYYY = "yyyy";
    public static final String MM = "MM";
    public static final String MM1 = "MM月";
    public static final String M = "M";
    public static final String DD = "dd";
    public static final String D = "d";
    public static final String MONTH_FORMAT = "yyyyMM";
    public static final String MONTH_FORMAT2 = "yyyy-MM";
    public static final String MM_DD_HH_MM = "MM月dd日 HH:mm";
    public static final String MM_DD_HH_MM_SS = "MM月dd日 HH:mm:ss";
    public static final String M_D_HH_MM_SS = "M月d日 HH:mm:ss";

    public static final String dateFormatSimpleReg = "[0-9]{4}-[0-9]{2}-[0-9]{2}";//匹配yyyy-MM-dd格式
    public static final String dateFormatReg = "[0-9]{4}-[0-9]{2}-[0-9]{2}\\s[0-9]{2}\\:[0-9]{2}\\:[0-9]{2}";//匹配yyyy-MM-dd HH:mm:ss格式
    /**
     * 格式化日期
     * @param dateStr
     * @param sourceFmt 原格式
     * @param targetFmt 目标格式
     * @return
     */
    public static String formatDate(String dateStr,String sourceFmt,String targetFmt){
        try {
            SimpleDateFormat sourceSdf = new SimpleDateFormat(sourceFmt);
            SimpleDateFormat targetSdf = new SimpleDateFormat(targetFmt);
            return targetSdf.format(sourceSdf.parse(dateStr));
        }catch (Exception e) {
            return dateStr;
        }
    }
    /**
     * 功能描述：获取当前系统时间
     *
     * @param parse 时间格式
     * @return
     * @throws ParseException <p>
     *                        修改历史 ：(修改人，修改时间，修改原因/内容)
     *                        </p>
     * @author bingzhong.qin
     * <p>
     * 创建日期 ：2014-1-2 下午2:35:54
     * </p>
     */
    public static Date getCurrentDate(String parse) throws ParseException {

        if (StringUtils.isBlank(parse)) {
            parse = DATETIME_FORMAT;
        }
        SimpleDateFormat df = new SimpleDateFormat(parse);
        return parse(df.format(new Date()), parse);
    }

    /**
     * 功能描述：string 转换成date，默认模式
     *
     * @param source
     * @return <p>
     * 修改历史 ：(修改人，修改时间，修改原因/内容)
     * </p>
     * @author bingzhong.qin
     * <p>
     * 创建日期 ：2013-12-31 下午2:15:31
     * </p>
     */
    public static Date parse(String source) {
        try {
            SimpleDateFormat format = new SimpleDateFormat();
            if (source.indexOf(":") > 0)
                format.applyPattern(DATETIME_FORMAT);
            else {
                format.applyPattern(DATE_FORMAT);
            }
            return format.parse(source.trim());
        } catch (ParseException e) {
        }
        return null;
    }


    /**
     * 功能描述：string 转换成date,自定义格式
     *
     * @param source
     * @param formatString
     * @return <p>
     * 修改历史 ：(修改人，修改时间，修改原因/内容)
     * </p>
     * @author bingzhong.qin
     * <p>
     * 创建日期 ：2013-12-31 下午2:14:30
     * </p>
     */
    public static Date parse(String source, String formatString) {
        try {
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern(formatString.trim());
            return format.parse(source.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 功能描述：date转换成string
     *
     * @param date
     * @param formatString
     * @return <p>
     * 修改历史 ：(修改人，修改时间，修改原因/内容)
     * </p>
     * @author bingzhong.qin
     * <p>
     * 创建日期 ：2013-12-31 下午2:11:42
     * </p>
     */
    public static String format(Date date, String formatString) {
        try {
            if (date == null)
                return "";
            if (StringUtils.isEmpty(formatString)) {
                formatString = DATETIME_FORMAT;
            }
            SimpleDateFormat format = new SimpleDateFormat(formatString);
            return format.format(date);
        }catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String format(long date, String formatString) {
        try {
            SimpleDateFormat format;
            if (StringUtils.isBlank(formatString)) {
                formatString = DATETIME_FORMAT;
            }
            format = new SimpleDateFormat(formatString);
            return format.format(date);
        }catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取上月月份
     * @return
     */
    public static int getPreMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(calendar.MONTH);
    }


    public static Long stringToLong(String time, String format) {
        if (null == format) {
            format = DateUtils.DATETIME_FORMAT;
        }
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
            return dateFormatter.parse(time).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 把时间戳类型转换为Date
     *
     * @param time
     * @return
     */
    public static String longToString(long time, String format) {
        try {
            Date date = new Date();
            date.setTime(time);
            if (StringUtils.isEmpty(format)) {
                format = DATETIME_FORMAT;
            }
            SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
            return dateFormatter.format(date);
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return int
     *          小于0 date1 > date2
     *          等于0 date1 = date2
     *          大于0 date1 < date2
     */
    public static Integer differentDays(String date1,String date1Fmt,String date2,String date2Fmt) {
        try {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(parse(date1,date1Fmt));

            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(parse(date2,date2Fmt));
            int day1= cal1.get(Calendar.DAY_OF_YEAR);
            int day2 = cal2.get(Calendar.DAY_OF_YEAR);

            int year1 = cal1.get(Calendar.YEAR);
            int year2 = cal2.get(Calendar.YEAR);
            if(year1 != year2) {
                //同一年
                int timeDistance = 0 ;
                for(int i = year1 ; i < year2 ; i ++) {
                    if(i%4==0 && i%100!=0 || i%400==0) {
                        //闰年
                        timeDistance += 366;
                    }
                    else {
                        //不是闰年
                        timeDistance += 365;
                    }
                }
                return timeDistance + (day2-day1) ;
            }
            else {
                //不同年
                return day2-day1;
            }
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取指定日期是周几
     *
     * @param date
     * @return
     */
    public static String getWeekStrByDate(Date date) {
        String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }

    /**
     * 获取指定日期是周几
     *
     * @param date
     * @return
     */
    public static String getWeekStrByDate(String date,String fmt) {
        try {
            String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
            Calendar cal = Calendar.getInstance();
            cal.setTime(parse(date,fmt));
            int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (week_index < 0) {
                week_index = 0;
            }
            return weeks[week_index];
        }catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 获取系统当前秒级时间戳
     *
     * @return
     */
    public static long getSecondStamp() {
        return System.currentTimeMillis() / 1000;
    }


    /**
     * 传入时间跟当前时间比较
     *
     * @param dateStr
     * @return
     */
    public static int compareDate(String dateStr) {
        DateFormat df = new SimpleDateFormat(DATETIME_FORMAT);
        Date newDate = new Date();
        Date paramDate;
        try {
            paramDate = df.parse(dateStr);
            return newDate.compareTo(paramDate);
        } catch (ParseException e) {
            return -2;
        }
    }

    /**
     * 比较两个date时间
     * @param dt1
     * @param dt2
     * @return
     */
    public static int compareDate(Date dt1, Date dt2) {
        try {
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }


    /**
     * 比较两个字符串时间
     * @param dateStr
     * @param dateStr1
     * @return
     */
    public static int compareDate(String dateStr,String dateStr1) {
        DateFormat df = new SimpleDateFormat(MONTH_FORMAT2);
        Date paramDate;
        Date paramDate1;
        try {
            paramDate = df.parse(dateStr);
            paramDate1 = df.parse(dateStr1);
            return paramDate.compareTo(paramDate1);
        } catch (ParseException e) {
            return -2;
        }
    }

    /**
     * 两个日期是否同一天
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    public static String getCurDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
        String s = sdf.format(new Date());
        return s;
    }

    /**
     * 获取当前日期为周几
     *
     * @return
     * @throws Exception
     */
    public static int getDayOfWeek() throws Exception {
        Calendar now = Calendar.getInstance();
        //一周第一天是否为星期天
        boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
        //获取周几
        int weekDay = now.get(Calendar.DAY_OF_WEEK);
        //若一周第一天为星期天，则-1
        if (isFirstSunday) {
            weekDay = weekDay - 1;
            if (weekDay == 0) {
                weekDay = 7;
            }
        }
        return weekDay;
    }

    /**
     * 判断当前日期是星期几<br>
     * <br>
     *
     * @param pTime 修要判断的时间<br>
     * @return dayForWeek 判断结果<br>
     * @Exception 发生异常<br>
     */
    public static int getDayOfWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    /**
     * 获取当前时间延后day天后的时间
     *
     * @param datetime
     * @param day
     * @param fmt
     * @return
     */
    public static String getDateAfterDay(Date datetime, int day, String fmt) {
        if (StringUtils.isEmpty(fmt)) {
            fmt = DATETIME_FORMAT;
        }
        DateFormat df = new SimpleDateFormat(fmt);
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(datetime);
            c.add(Calendar.DATE, day);
            Date d = c.getTime();
            return df.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前时间延后hour小时后的时间
     *
     * @param datetime
     * @return
     */
    public static String getDateAfterHour(String datetime, int hour) {
        DateFormat df = new SimpleDateFormat(TIME_FORMAT);
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(df.parse(datetime));
            c.add(Calendar.HOUR, hour);
            Date d = c.getTime();
            return df.format(d);
        } catch (ParseException e) {
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 某月第一天
     *
     * @return
     */
    public static String getFirstDayInMonth(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date theDate = calendar.getTime();

        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        return str.toString();

    }

    /**
     * 某月最后一天
     *
     * @return
     */
    public static String getLastDayInMonth(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date theDate = calendar.getTime();
        String s = df.format(theDate);
        StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");
        return str.toString();

    }

    /**
     * 将日期时间字符串转换为Long
     *
     * @param format
     * @param time
     * @return
     */
    public static Long formatToLong(String format, String time) {
        try {
            SimpleDateFormat sd = new SimpleDateFormat(format);
            return sd.parse(time).getTime();
        } catch (Exception e) {
            Log.e("dd", "时间转换成long异常： " + e.getMessage());
        }
        return null;
    }


    /**
     * 获得指定日期的前N天
     *
     * @param beforeDay 前N天
     * @return
     * @throws Exception
     * @auhor dengjh
     */
    public static String getSpecifiedDayBefore(Integer beforeDay) {
        Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
        calendar.add(Calendar.DATE, -beforeDay);    //得到前一天
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * 获取当前时间延后month月第一天的时间
     *
     * @param month
     * @return
     */
    public static String getMontAfterFirsetDay(int month) {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date datetime = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(datetime);
            c.add(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, 1);
            Date d = c.getTime();
            return df.format(d);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取当前时间延后month月最后一天的时间
     *
     * @param month
     * @return
     */
    public static String getMontAfterLastDay(int month) {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date datetime = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(datetime);
            c.add(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, 31);
            Date d = c.getTime();
            return df.format(d);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取当前日期的第一天
     * @param dateStr 日期字符串，格式： yyyy-MM-dd
     * @return
     */
    public static String getMonthFirstDate(String dateStr) {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(df.parse(dateStr));
            calendar.set(Calendar.DAY_OF_MONTH,1);
            return df.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前月份的最后一天
     * @param dateStr 日期字符串，格式：yyyy-MM-dd
     * @return
     */
    public static String getMonthLastDate(String dateStr) {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(df.parse(dateStr));
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 0);
            return df.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getWeek(String createTime) {
        try {
            long currentTime = DateUtils.getDateByPattern(new Date(), DateUtils.ZEROTIME_FORMAT).getTime();//获取目前0点时间
            long itemCretime = DateUtils.getTimeByPattern(createTime, DateUtils.DATETIME_FORMAT).getTime();//获取账单创建的时间
            long time = (currentTime - itemCretime) / 1000;
            if (time <= 0) {
                return "今天";
            } else if (time > 0 && time <= 3600 * 24) {
                return "昨天";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DateUtils.getWeekStrByDate(DateUtils.parse(createTime));
    }

    @NonNull
    public static String  gethhMMTime(Date date){
        String string=null;
        if(date.getHours()<12){
            string="上午";
        }else{
            string="下午";
        }
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT3);
        return string+format.format(date);
    }

    /***************************************************************************
     * stringToDate 把字符型"yyyy-MM-dd hh:mm:ss"转换成日期型
     *
     * @param s String 需要转换的日期时间字符串
     * @return theDate Date
     **************************************************************************/
    public static Date stringToDate(String s) {
        Date theDate = null;
        try {
            if (!StringUtils.isEmpty(s)) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat(DATETIME_FORMAT);
                theDate = dateFormatter.parse(s);
            } else {
                theDate = null;
            }
        } catch (ParseException pe) {
            // plogger.error(e); e.printStackTrace();
            theDate = null;
        }
        return theDate;
    }

    public static Date stringToDate(String s, String format) {
        Date theDate = null;
        if (format == null) {
            format = DATETIME_FORMAT;
        }
        try {
            if (s != null) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
                theDate = dateFormatter.parse(s);
            } else {
                theDate = null;
            }
        } catch (ParseException pe) {
            // plogger.error(e); e.printStackTrace();
            theDate = null;
        }
        return theDate;
    }


    public static String format(String date,String sourceFmt, String targetFmt) {
        return format(stringToDate(date,sourceFmt), targetFmt);
    }

    public static String format(String date, String formatString) {
        return format(stringToDate(date), formatString);
    }

    public static String getMonth(String timeStr) {
        return DateUtils.format(timeStr, DateUtils.MONTH_FORMAT2);
    }

    public static String getMonth(String timeStr,String fmt) {
        return DateUtils.format(timeStr, fmt);
    }

    public static String getMonth(String timeStr,String sourceFmt,String fmt) {
        return DateUtils.format(timeStr,sourceFmt, fmt);
    }

    public static String getHouse(String timeStr) {
        try {
            long currentTime = DateUtils.getDateByPattern(new Date(), DateUtils.ZEROTIME_FORMAT).getTime();//获取目前0点时间
            long itemCretime = DateUtils.getTimeByPattern(timeStr, DateUtils.DATETIME_FORMAT).getTime();//获取账单创建的时间
            long time = (currentTime - itemCretime) / 1000;
            if (time <= 3600 * 24) {
                return DateUtils.format(timeStr, DateUtils.TIME_FORMAT2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DateUtils.format(timeStr, DateUtils.M2_D2_FORMAT);

    }

    public static Date getTimeByPattern(String time, String dateFormat) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.parse(time);
    }

    public static Date getDateByPattern(Date time, String cutFormat) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(cutFormat);
        String newDateStr = sdf.format(time);
        sdf = new SimpleDateFormat(DATETIME_FORMAT);
        return sdf.parse(newDateStr);
    }

    /**
     * 传入的日期是否是当月
     * @param dateStr
     * @return
     */
    public static boolean isThisMonth(String dateStr) {
        return StringUtils.isNotEmpty(dateStr) && dateStr.equals(getMonthFirstDate(DateUtils.getMontAfterFirsetDay(0)));
    }
}
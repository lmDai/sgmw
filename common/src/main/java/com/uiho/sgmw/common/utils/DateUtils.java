package com.uiho.sgmw.common.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 作者：uiho_mac
 * 时间：2018/5/10
 * 描述：
 * 版本：1.0
 * 修订历史：
 */

public class DateUtils {

    /**
     * 日期类型 *
     */
    public static final String yyyyMMDD = "yyyy/MM/dd";
    public static final String divideyyyyMMDD = "yyyy/MM/dd";
    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String HHmmss = "HH:mm:ss";
    public static final String HHmm = "HH:mm";
    public static final String LOCALE_DATE_FORMAT = "yyyy年M月d日 HH:mm:ss";
    public static final String DB_DATA_FORMAT = "yyyy-MM-DD HH:mm:ss";
    public static final String NEWS_ITEM_DATE_FORMAT = "hh:mm M月d日 yyyy";
    public static final String yyyyMM = "yyyy-MM";
    public static final String yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
    public static final String MMddHHmm = "MM/dd HH:mm";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String MMdd = "MM-dd";

    public static Date stringToDate(String dateStr, String pattern)
            throws Exception {
        return new SimpleDateFormat(pattern).parse(dateStr);
    }

    /**
     * 将Date类型转换为日期字符串
     *
     * @param date Date对象
     * @param type 需要的日期格式
     * @return 按照需求格式的日期字符串
     */
    public static String formatDate(Date date, String type) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(type);
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatDateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String getWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int i = cal.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
            default:
                return "";
        }
    }

    /**
     * 获取当前日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static String getWeekFirst(String date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat(yyyyMMDD).parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        //所在周开始日期
        return new SimpleDateFormat(yyyyMMDD).format(cal.getTime());
    }

    /**
     * 获取当前日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static String getWeekLast(String date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat(yyyyMMDD).parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        //所在周结束日期
        return new SimpleDateFormat(yyyyMMDD).format(cal.getTime());
    }

    /**
     * 获取某月的第一天
     *
     * @param date
     * @return
     */
    public static String getMonthFirst(String date) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat(yyyyMMDD).parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return new SimpleDateFormat(yyyyMMDD).format(calendar.getTime());
    }

    /**
     * 获取某月的最后一天
     *
     * @param date
     * @return
     */
    public static String getMonthLast(String date) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat(yyyyMMDD).parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return new SimpleDateFormat(yyyyMMDD).format(calendar.getTime());
    }


    /**
     * 获取某年的第一天
     *
     * @param date
     * @return
     */
    public static String getYearFirstMonth(String date) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat(yyyyMM).parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return new SimpleDateFormat(yyyyMM).format(calendar.getTime());
    }

    /**
     * 获取某年的最后一天
     *
     * @param date
     * @return
     */
    public static String getYearLastMonth(String date) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat(yyyyMM).parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.getTime();
        calendar.set(Calendar.DAY_OF_YEAR,
                calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        return new SimpleDateFormat(yyyyMM).format(calendar.getTime());
    }

    /**
     * 获取当前日期的前一天
     *
     * @return
     */
    public static String getPreDayString() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return formatDateToString(calendar.getTime(), DateUtils.yyyyMMDD);
    }

    //获取下一天
    public static Date getNextDay(Date date, int flag) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, flag);
        return calendar.getTime();
    }

    public static String getNextDay(String date, int flag) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat(yyyyMMDD).parse(date));
            calendar.add(Calendar.DAY_OF_MONTH, flag);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat(yyyyMMDD).format(calendar.getTime());
    }

    //获取下一天
    public static Date getNextMonth(Date date, int flag) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, flag);
        return calendar.getTime();
    }

    //获取下一月
    public static String getNextMonth(String  date, int flag) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat(yyyyMM).parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.MONTH, flag);
        return new SimpleDateFormat(yyyyMM).format(calendar.getTime());
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatterDate(String formatter, String newFormatter, String str) {
        Date date = null;
        String now = null;
        try {
            date = new SimpleDateFormat(formatter).parse(str);
            now = new SimpleDateFormat(newFormatter).format(date);
        } catch (ParseException e) {
            return null;
        }
        return now;
    }

    /**
     * 比较日期大小 string
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean compareMonth(String date1, String date2) {
        DateFormat dateFormat = new SimpleDateFormat(yyyyMM);
        try {
            Date d1 = dateFormat.parse(date1);
            Date d2 = dateFormat.parse(date2);
            if (d1.equals(d2)) {
                return false;
            } else if (d1.before(d2)) {
                return true;
            } else if (d1.after(d2)) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        return false;
    }


    /**
     * 比较日期大小 string
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean compareDate(String date1, String date2) {
        DateFormat dateFormat = new SimpleDateFormat(yyyyMMDD);
        try {
            Date d1 = dateFormat.parse(date1);
            Date d2 = dateFormat.parse(date2);
            if (d1.equals(d2)) {
                return false;
            } else if (d1.before(d2)) {
                return true;
            } else if (d1.after(d2)) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        return false;
    }

    /**
     * 获取今天
     *
     * @return
     */
    public static String getNowDay() {
        try {
            SimpleDateFormat df = new SimpleDateFormat(yyyyMMDD);
            return df.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取今天
     *
     * @return
     */
    public static String getCurrentMonth() {
        try {
            SimpleDateFormat df = new SimpleDateFormat(yyyyMM);
            return df.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

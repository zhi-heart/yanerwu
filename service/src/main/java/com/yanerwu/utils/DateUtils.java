package com.yanerwu.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class DateUtils {

    public static final String pattern = "yyyy-MM-dd";
    public static final String fmt = "yyyy-MM-dd HH:mm:ss";
    public static final SimpleDateFormat df = new SimpleDateFormat(fmt);
    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /**
     * 获取历史时间
     *
     * @param date 当前时间
     * @param day  历史天数
     * @return
     */
    public static String getBackDate(String date, int day) {
        String backDate = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date sd = format.parse(date);
            backDate = getBackDate(sd, day);
        } catch (ParseException e) {
            logger.error("获取历史时间出错。", e);
        }
        return backDate;
    }

    /**
     * 获取历史时间
     *
     * @param date 当前时间
     * @param day  历史天数
     * @return
     */
    public static String getBackDate(Date date, int day) {
        String result = "";
        try {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            int d = calendar.get(Calendar.DAY_OF_YEAR);
            calendar.set(Calendar.DAY_OF_YEAR, d - day);
            result = DateFormatUtils.ISO_DATE_FORMAT.format(calendar.getTime());
        } catch (Exception e) {
            logger.error("获取历史时间出错。", e);
        }
        return result;
    }

    /**
     * 获取历史时间
     *
     * @param day 历史天数
     * @return
     */
    public static String getBackDate(int day) {
        String result = "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, day);
            result = DateFormatUtils.ISO_DATE_FORMAT.format(calendar.getTime());
        } catch (Exception e) {
            logger.error("获取历史时间出错。", e);
        }
        return result;
    }

    /**
     * 获取未来时间
     *
     * @param date 当前时间
     * @param day  未来天数
     * @return
     */
    public static String getFrontDate(Date date, int day) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int d = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.set(Calendar.DAY_OF_YEAR, d + day);
        return DateFormatUtils.ISO_DATE_FORMAT.format(calendar.getTime());
    }

    /**
     * 获取未来时间
     *
     * @param date 当前时间
     * @param day  未来天数
     * @return
     */
    public static String getFrontDate(String date, int day) {
        String frontDate = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date sd = format.parse(date);
            frontDate = getFrontDate(sd, day);
        } catch (ParseException e) {
            logger.error("获取未来时间出错。", e);
        }
        return frontDate;
    }

    /**
     * 获取上周的开始时间
     *
     * @param date 开始时间
     * @return
     */
    public static String getWeekStartDate(String date) {
        String result = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date sd = format.parse(date);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(sd);
            calendar.add(Calendar.WEEK_OF_YEAR, -1);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            result = DateFormatUtils.ISO_DATE_FORMAT.format(calendar) + " 00:00:00";
        } catch (ParseException e) {
            logger.error("获取周开始时间出错。", e);
        }
        return result;
    }

    /**
     * 获取上周的结束时间
     *
     * @param date 开始时间
     * @return
     */
    public static String getWeekEndDate(String date) {
        String result = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date sd = format.parse(date);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(sd);
            calendar.add(Calendar.WEEK_OF_YEAR, -1);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            result = DateFormatUtils.ISO_DATE_FORMAT.format(calendar) + " 23:59:59";
        } catch (ParseException e) {
            logger.error("获取周结束时间出错。", e);
        }
        return result;
    }

    public static Map<String, String> getHalfYear(String date) {
        Map<String, String> reuslt = new HashMap<>();
        for (int i = 1; i <= 6; i++) {
            String startDate = getStartMonthDate(date);
            String endDate = getEndMonthDate(date);
            reuslt.put(startDate, endDate);
            if (i != 0) {
                date = startDate;
            }
        }
        return reuslt;
    }

    /**
     * 获取上个月的开始时间
     *
     * @param date 当前时间
     * @return
     */
    public static String getStartMonthDate(String date) {
        String result = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date sd = format.parse(date);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(sd);
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            result = DateFormatUtils.ISO_DATE_FORMAT.format(calendar);
        } catch (ParseException e) {
            logger.error("获取上个月的开始时间。", e);
        }
        return result;
    }

    /**
     * 获取上个月的月末时间
     *
     * @param date 当前时间
     * @return
     */
    public static String getEndMonthDate(String date) {
        String result = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date sd = format.parse(date);
            Calendar cale = Calendar.getInstance();
            cale.setTime(sd);
            cale.set(Calendar.DAY_OF_MONTH, 0);
            result = DateFormatUtils.ISO_DATE_FORMAT.format(cale.getTime());
        } catch (ParseException e) {
            logger.error(String.format("%s 获取月末时间出错。", date), e);
        }
        return result;
    }

    /**
     * 获取月份起始日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getMinMonthDate(String date) {
        String result = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date sd = format.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sd);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            result = DateFormatUtils.ISO_DATE_FORMAT.format(calendar.getTime()) + " 00:00:00";
        } catch (ParseException e) {
            logger.error("获取月开始时间出错。", e);
        }
        return result;
    }

    /**
     * 获取月份最后日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getMaxMonthDate(String date) {
        String result = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date sd = format.parse(date);
            result = getMaxMonthDate(sd);
        } catch (ParseException e) {
            logger.error("获取月结束时间出错。", e);
        }
        return result;
    }

    public static String getMaxMonthDate(Date sd) {
        String result = "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sd);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            result = DateFormatUtils.ISO_DATE_FORMAT.format(calendar.getTime()) + " 23:59:59";
        } catch (Exception e) {
            logger.error("获取月结束时间出错。", e);
        }
        return result;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(String smdate, String bdate) {
        int day = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date sd = format.parse(smdate);
            Date bd = format.parse(bdate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sd);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bd);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            day = Integer.parseInt(String.valueOf(between_days));
        } catch (Exception e) {
            logger.error(String.format("获取两个时间的间隔天数出错，开始时间 %s ， 结束时间 %s", smdate, bdate), e);
        }
        return day;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(String smdate, Date bdate) {
        int day = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date sd = format.parse(smdate);
            Date bd = bdate;
            Calendar cal = Calendar.getInstance();
            cal.setTime(sd);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bd);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            day = Integer.parseInt(String.valueOf(between_days));
        } catch (Exception e) {
            logger.error(String.format("获取两个时间的间隔天数出错，开始时间 %s ， 结束时间 %s", smdate, bdate), e);
        }
        return day;
    }

    /**
     * 判断是否星期一
     *
     * @return
     */
    public static boolean isMonday() {
        boolean bool = false;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 1) {
            bool = true;
        }
        return bool;
    }

    /**
     * 获取开始时间和结束时间之间的天
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    public static List<String> getDatesBetweenTwoDay(String beginDate, String endDate) {
        List<String> dates = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date sd = format.parse(beginDate);
            dates = new ArrayList<String>();
            int days = daysBetween(beginDate, endDate);
            for (int i = 0; i <= days; i++) {
                dates.add(getFrontDate(sd, i));
            }
        } catch (ParseException e) {
            logger.error("获取开始时间和结束时间之间出错。", e);
        }
        return dates;
    }

    public static String[] getDatesBetweenTwoDayArray(String beginDate, String endDate) {
        String[] dates = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date sd = format.parse(beginDate);
            int days = daysBetween(beginDate, endDate);
            dates = new String[days + 1];
            for (int i = 0; i <= days; i++) {
                dates[i] = getFrontDate(sd, i);
            }
        } catch (ParseException e) {
            logger.error("获取开始时间和结束时间之间出错。", e);
        }
        return dates;
    }

    /**
     * 获取两个时间段内的周时间
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    public static Map<String, String> getDatesBetweenTwoWeek(String beginDate, String endDate) {
        Map<String, String> weekMap = null;
        try {
            weekMap = new TreeMap<String, String>();
            List<String> days = getDatesBetweenTwoDay(beginDate, endDate);
            for (int i = 0; i < days.size(); i++) {
                weekMap.put(getWeekStartDate(days.get(i)), getWeekEndDate(days.get(i)));
            }
        } catch (Exception e) {
            logger.error("获取周开始和结束时间集合出错。", e);
        }
        return weekMap;
    }

    /**
     * 获取两个时间段内的月时间
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    public static Map<String, String> getDatesBetweenTwoMonth(String beginDate, String endDate) {
        Map<String, String> monthMap = null;
        try {
            monthMap = new TreeMap<String, String>();
            List<String> days = getDatesBetweenTwoDay(beginDate, endDate);
            for (int i = 0; i < days.size(); i++) {
                monthMap.put(getMinMonthDate(days.get(i)), getMaxMonthDate(days.get(i)));
            }
        } catch (Exception e) {
            logger.error("获取周开始和结束时间集合出错。", e);
        }
        return monthMap;
    }

    /**
     * 字符串时间转换成long
     *
     * @param data
     * @return
     */
    public static long fmtStrDataToLong(String data) {
        long time = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date dt = format.parse(data);
            time = dt.getTime() / 1000;
        } catch (ParseException e) {
            logger.error(String.format("string 转换 long 失败，string 值 %s", data), e);
        }
        return time;
    }

    /**
     * 字符串时间转换成long
     *
     * @param data
     * @return
     */
    public static long fmtStrDataToLong(String data, String fmt) {
        long time = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat(fmt);
            Date dt = format.parse(data);
            time = dt.getTime() / 1000;
        } catch (ParseException e) {
            logger.error(String.format("string 转换 long 失败，string 值 %s", data), e);
        }
        return time;
    }

    /**
     * 计算两个时间相差的月
     *
     * @param onlineDate 上线时间
     * @param pubTime    当前时间
     * @return int 返回类型<b>
     */
    public static int getTimeOperation(String onlineDate, String pubTime) {
        int nY = Integer.parseInt(pubTime.split("-")[0]);
        int nM = Integer.parseInt(pubTime.split("-")[1]);

        int oY = Integer.parseInt(onlineDate.split("-")[0]);
        int oM = Integer.parseInt(onlineDate.split("-")[1]);

        int i = nY - oY;
        if (i > 0) {
            i = i * 12;
        }
        int result = (i + nM) - oM;
        return result > 0 ? result - 1 : result;
    }

    /**
     * 验证时间格式
     *
     * @param successTimeDate
     * @return
     * @throws RuntimeException
     */
    public static boolean isValidDate(String successTimeDate) throws RuntimeException {
        boolean bool = false;
        try {
            DateFormat format = new SimpleDateFormat(fmt);
            Date date = (Date) format.parse(successTimeDate);
            String strNew = format.format(date);
            if (!strNew.equals(successTimeDate)) {
                // throw new RuntimeException(String.format("验证值 %s 时间格式出错。",
                // successTimeDate));
                // logger.error(String.format("验证值 %s 时间格式出错。",
                // successTimeDate));
            }
            bool = true;
        } catch (Exception e) {
            // throw new RuntimeException(String.format("验证的值 %s 时间格式出错。",
            // successTimeDate));
            // logger.error(String.format("验证值 %s 时间格式出错。", successTimeDate));
        }
        return bool;
    }

    /**
     * <p>
     * 判断是否是月初
     * </p>
     *
     * @return boolean 返回类型<b>
     */
    public static boolean isMonthBeginning() {
        boolean bool = false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (calendar.get(Calendar.DATE) == calendar.getActualMinimum(Calendar.DAY_OF_MONTH)) {
            bool = true;
        }
        return bool;
    }

    /**
     * <p>
     * 获取当前月份
     * </p>
     *
     * @return int 返回类型<b>
     */
    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * String转Date
     *
     * @param strDate
     * @param pattern
     * @return
     * @throws ParseException Date 返回类型<b>
     */
    public static Date parse(String strDate, String pattern) {
        Date date = null;
        try {
            date = StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(pattern).parse(strDate);
        } catch (Exception e) {
            logger.error(String.format("String转换Date失败，String值：%s", strDate), e);
        }
        return date;
    }

    public static String getNowTime() {
        return new SimpleDateFormat(fmt).format(Calendar.getInstance().getTime());
    }

    /**
     * 获取月最大天数
     *
     * @param date
     * @return
     */
    public static int getMonthMaxDayByDate(String date) {
        int maxDay = -1;

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateFormatUtils.ISO_DATE_FORMAT.parse(date));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            maxDay = calendar.get(calendar.DATE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return maxDay;
    }

    /**
     * 根据开始时间,期限 获取自然月月份 直接除30会有问题 必须加锁,Calendar非线程安全
     *
     * @param startDate
     * @param limit
     * @return
     */
    public static synchronized Double getMonthCount(String startDate, double limit) {
        String endDate = "";
        double firstMonthDay = -1, lastMonthDay = -1, monthCount = 0d;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(df.parse(startDate));
            firstMonthDay = cal.get(Calendar.DATE);
            cal.add(Calendar.DAY_OF_YEAR, (int) limit);
            lastMonthDay = cal.get(Calendar.DATE);
            endDate = DateFormatUtils.ISO_DATE_FORMAT.format(cal.getTime());

            // 获取第一个月最大天数
            double firstMonthMaxDay = DateUtils.getMonthMaxDayByDate(startDate);
            // 获取最后一个月最大天数
            double lastMonthMaxDay = DateUtils.getMonthMaxDayByDate(endDate);

            if (startDate.substring(0, startDate.lastIndexOf("-")).equals(endDate.substring(0, endDate.lastIndexOf("-")))) {
                // 不跨月
                monthCount = (lastMonthDay - firstMonthDay) / firstMonthMaxDay;
            } else {
                // 跨月
                // 计算第一月
                monthCount = (firstMonthMaxDay - firstMonthDay) / firstMonthMaxDay;
                // 计算中间月份
                monthCount += DateUtils.getTimeOperation(startDate, endDate);
                // 计算最后一月
                monthCount += lastMonthDay / lastMonthMaxDay;

            }
        } catch (Exception e) {
            logger.error("计算月份出错", e);
        }
        return monthCount;
    }

    public static String getWeekData(int week) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, week * 7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return DateFormatUtils.ISO_DATE_FORMAT.format(cal.getTime());
    }

    /**
     * 获取一组时间最小的时间
     *
     * @param dates
     * @return
     * @throws ParseException
     */
    public static String getStartDateByDates(String... dates) throws ParseException {
        String startDate = dates[1];
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        long sl = format.parse(startDate).getTime();
        for (String date : dates) {
            long l = format.parse(date).getTime();
            if (l < sl) {
                startDate = date;
            }
        }
        return startDate;
    }

    /**
     * 获取一组时间最大的时间
     *
     * @param dates
     * @return
     * @throws ParseException
     */
    public static String getEndDateByDates(String... dates) throws ParseException {
        String startDate = dates[1];
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        long sl = format.parse(startDate).getTime();
        for (String date : dates) {
            long l = format.parse(date).getTime();
            if (l > sl) {
                startDate = date;
            }
        }
        return startDate;
    }

}
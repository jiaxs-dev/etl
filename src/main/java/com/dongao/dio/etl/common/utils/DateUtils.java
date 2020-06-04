package com.dongao.dio.etl.common.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @author jiaxiansun@dongao.com
 * @date 2019/10/12 10:16
 * @description
 */
public class DateUtils {
    public final static String pattern = "yyyy-MM-dd HH:mm:ss";
    public final static String pattern_utc = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static Date formatStr2Date(String str) throws Exception {
        if (null != str && !"".equals(str)) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(str);
        } else {
            return null;
        }
    }

    public static String formatDate2Str(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
    /**
     * 第一步UTC时间转LocalDateTime
     */
    public static LocalDateTime UTC2Local(String utcStr) throws Exception {
        ZonedDateTime zdt = ZonedDateTime.parse(utcStr);
        LocalDateTime localDateTime = zdt.toLocalDateTime();
        return localDateTime.plusHours(8);
    }

    /**
     * 第二步获取系统默认时区，根据市区转化为Date类型
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);//Combines this date-time with a time-zone to create a  ZonedDateTime.
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * 第三步采用SimpleDateFormat把Date类型转化为你想要的格式
     */
    public static String formatDate2Str(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date formatDate2UtcStr(String localTime) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date localDate = sdf.parse(localTime);
        long localTimeInMillis = localDate.getTime();
        /** long时间转换成Calendar */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(localTimeInMillis);
        /** 取得时间偏移量 */
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        /** 取得夏令时差 */
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        /** 从本地时间里扣除这些差量，即可以取得UTC时间*/
        calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        /** 取得的时间就是UTC标准时间 */
        Date utcDate = new Date(calendar.getTimeInMillis());
        return utcDate;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(formatDate2Str(formatDate2UtcStr("2020-03-11 13:32:12"),pattern_utc));
    }
}

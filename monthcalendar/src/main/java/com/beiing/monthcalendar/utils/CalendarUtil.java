package com.beiing.monthcalendar.utils;

import org.joda.time.DateTime;

/**
 * Created by linechen on 2017/5/19.<br/>
 * 描述：
 * </br>
 */

public class CalendarUtil {
    public static boolean isSameDay(DateTime t1, DateTime t2){
        return t1.toString("yyyyMMdd").equals(t2.toString("yyyyMMdd"));
    }

    public static boolean isToday(DateTime t){
        return isSameDay(t, new DateTime());
    }

    public static boolean isThisMonth(DateTime current, DateTime t2){
        return (current.getYear() == t2.getYear()) && (current.getMonthOfYear() == t2.getMonthOfYear());
    }
}

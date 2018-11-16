package com.bluetroy.crawler91.crawler.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author heyixin
 */
public class TimeUtils {
    private static final String FORMAT = "y年M月d日H时";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(FORMAT);

    public static String getDate() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

    public static String getDateByTimeBefore(String timeBefore) throws Exception {
        return getLocalDateTime(timeBefore).format(DATE_TIME_FORMATTER);
    }

    public static LocalDateTime parse(String time) {
        return LocalDateTime.parse(time, DATE_TIME_FORMATTER);
    }

    public static String format(LocalDateTime time) {
        return time.format(DATE_TIME_FORMATTER);
    }


    private static LocalDateTime getLocalDateTime(String addTimeBeforeNow) throws Exception {
        int indexOfYear = addTimeBeforeNow.indexOf("年");
        int indexOfDay = addTimeBeforeNow.indexOf("天");
        int indexOfHour = addTimeBeforeNow.indexOf("小时");
        if (indexOfYear != -1) {
            int year = Integer.parseInt(addTimeBeforeNow.substring(0, indexOfYear));
            return LocalDateTime.now().minusYears(year);
        }
        if (indexOfDay != -1) {
            int day = Integer.parseInt(addTimeBeforeNow.substring(0, indexOfDay));
            return LocalDateTime.now().minusDays(day);
        }
        if (indexOfHour != -1) {
            int hour = Integer.parseInt(addTimeBeforeNow.substring(0, indexOfHour));
            return LocalDateTime.now().minusHours(hour);
        }
        throw new Exception("time： " + addTimeBeforeNow + " parse error");
    }
}

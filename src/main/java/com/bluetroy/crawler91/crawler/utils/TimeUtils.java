package com.bluetroy.crawler91.crawler.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author heyixin
 */
public class TimeUtils {
    private static final String FORMAT = "y年M月d日h时";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(FORMAT);

    public static String getDate() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

    public static String getDate(String timeBefore) throws Exception {
        return getLocalDateTime(timeBefore).format(DATE_TIME_FORMATTER);
    }


    private static LocalDateTime getLocalDateTime(String addTimeBeforeNow) throws Exception {
        int yearsBefore = addTimeBeforeNow.indexOf("年");
        int daysBefore = addTimeBeforeNow.indexOf("天");
        int hoursBefore = addTimeBeforeNow.indexOf("小时");
        if (yearsBefore != -1) {
            return LocalDateTime.now().minusYears(yearsBefore);
        }
        if (daysBefore != -1) {
            return LocalDateTime.now().minusDays(daysBefore);
        }
        if (hoursBefore != -1) {
            return LocalDateTime.now().minusHours(hoursBefore);
        }
        throw new Exception("time： " + addTimeBeforeNow + " parse error");
    }
}

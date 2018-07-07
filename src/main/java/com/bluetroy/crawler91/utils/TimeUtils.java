package com.bluetroy.crawler91.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author heyixin
 */
public class TimeUtils {
    private static final String FORMAT = "yyyy年MM月dd日 hh:mm a";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(FORMAT);

    public static String getDate() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }
}

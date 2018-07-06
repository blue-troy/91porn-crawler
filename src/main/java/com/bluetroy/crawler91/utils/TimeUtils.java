package com.bluetroy.crawler91.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getDate() {
        return df.format(new Date());
    }
}

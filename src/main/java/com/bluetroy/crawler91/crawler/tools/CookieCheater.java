package com.bluetroy.crawler91.crawler.tools;

import java.net.HttpCookie;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-08
 * Time: 3:07 PM
 */
public class CookieCheater {
    private static final String WATCH_TIMES = "watch_times";

    public static void resetWatchTimes(HttpCookie httpCookie) {
        if (WATCH_TIMES.equals(httpCookie.getName())) {
            httpCookie.setValue("1");
        } else {
            throw new IllegalArgumentException("穿入的cookie并非 " + WATCH_TIMES);
        }
    }
}

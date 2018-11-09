package com.bluetroy.crawler91.crawler.utils;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-08
 * Time: 9:09 AM
 */
public class CookieUtils {
    private static CookieManager COOKIE_MANAGER = new CookieManager(new CrackLimitCookieStore(), null);

    static {
        CookieHandler.setDefault(COOKIE_MANAGER);
    }

    public static void init() {

    }

    public static List<HttpCookie> getCookies() {
        return COOKIE_MANAGER.getCookieStore().getCookies();
    }

    public static void printCookies() {
        getCookies().forEach(httpCookie -> System.out.println(httpCookie.toString()));
    }

    public static HttpCookie getCookie(String name) throws ClassNotFoundException {
        for (HttpCookie httpCookie : getCookies()) {
            if (httpCookie.getName().equals(name)) {
                return httpCookie;
            }
        }
        throw new ClassNotFoundException("没有对应的cookie");
    }

    public static void replaceCookie(String name, String newValue) throws ClassNotFoundException {
        getCookie(name).setValue(newValue);
    }
}

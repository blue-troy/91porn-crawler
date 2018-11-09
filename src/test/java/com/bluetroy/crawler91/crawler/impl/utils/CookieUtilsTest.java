package com.bluetroy.crawler91.crawler.impl.utils;

import com.bluetroy.crawler91.crawler.impl.tools.HttpClient;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-08
 * Time: 9:42 AM
 */
public class CookieUtilsTest {

    @Test
    public void getCookies() throws Exception {
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "1087");
        HttpClient.getNow("http://91porn.com/index.php");
        CookieUtils.printCookies();
        CookieUtils.replaceCookie("CLIPSHARE", "fucker");
        CookieUtils.printCookies();
    }
}
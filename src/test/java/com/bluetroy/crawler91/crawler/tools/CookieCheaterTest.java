package com.bluetroy.crawler91.crawler.tools;

import com.bluetroy.crawler91.crawler.UserAuthenticator;
import com.bluetroy.crawler91.crawler.utils.CookieUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.CookiePolicy;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-08
 * Time: 3:16 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CookieCheaterTest {
    @Autowired
    UserAuthenticator userAuthenticator;

    @Test
    public void test() throws Exception {
        boolean isLogin = userAuthenticator.login("oldblueman", "lx032515.jp", "8752");
        System.out.println(isLogin);
        CookieUtils.printCookies();
        if (isLogin) {
            request();
            CookieUtils.printCookies();
            request();
            CookieUtils.printCookies();
            request();
            CookieUtils.printCookies();
        }
    }

    private void request() throws Exception {
        HttpClient.getNow("http://91porn.com/view_video.php?viewkey=66fa5dafe3c2568ff4e0");
    }
}
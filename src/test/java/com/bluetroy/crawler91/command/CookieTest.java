package com.bluetroy.crawler91.command;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-10-28
 * Time: 4:19 PM
 */
public class CookieTest {
    public static void requestURL() throws Exception {
        URL url = new URL("http://localhost:8080/test/cookie");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //String basic = Base64.getEncoder().encodeToString("infcn:123456".getBytes());
        //conn.setRequestProperty("Proxy-authorization", "Basic " + basic);
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
    }

    @Test
    public void test() throws Exception {
        CookieManager manager = new CookieManager();
        CookieHandler.setDefault(manager);
        requestURL();
        SecondRequest.requestURL();

    }
}

class SecondRequest {
    public static void requestURL() throws Exception {
        URL url = new URL("http://localhost:8080/test/cookie");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //String basic = Base64.getEncoder().encodeToString("infcn:123456".getBytes());
        //conn.setRequestProperty("Proxy-authorization", "Basic " + basic);
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
    }
}

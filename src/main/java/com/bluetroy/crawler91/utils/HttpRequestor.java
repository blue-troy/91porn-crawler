package com.bluetroy.crawler91.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author heyixin
 */
public class HttpRequestor {
    private static final Integer NOT_SUCCESS_RESPONSE_CODE = 300;

    static {
        CookieManager manager = new CookieManager();
        CookieHandler.setDefault(manager);
        String host = "http://91porn.com/";
    }

    public static String get(URL url) throws Exception {
        System.out.println("get  " + url.toString());
        StringBuilder stringBuffer = new StringBuilder();
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (iPad; CPU OS 11_0 like Mac OS X) AppleWebKit/604.1.34 (KHTML, like Gecko) Version/11.0 Mobile/15A5341f Safari/604.1");
        if (httpURLConnection.getResponseCode() >= NOT_SUCCESS_RESPONSE_CODE) {
            throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\r\n");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return stringBuffer.toString();
    }

    public static String get(String url) throws Exception {
        return get(new URL(url));
    }

    public static void download(String url, String filename) throws IOException {
        try (InputStream inputStream = new URL(url).openStream()) {
            Path filePath = Paths.get(filename);
            if (Files.notExists(filePath)) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    public static void download(String url, String filename, String dir) throws IOException {
        Path target = Paths.get(dir, filename);
        Files.createDirectory(target.getParent());
        download(url, target.toString());
    }
}


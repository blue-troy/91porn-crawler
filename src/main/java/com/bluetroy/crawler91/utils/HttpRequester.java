package com.bluetroy.crawler91.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.*;

/**
 * @author heyixin
 */
@Log4j2
public class HttpRequester {
    private static final ExecutorService HTTP_GET_SERVICE;
    private static final ExecutorService DOWNLOAD_SERVICE;
    private static final Integer NOT_SUCCESS_RESPONSE_CODE = 300;
    private static final int TEN_MINUTE = 10 * 60 * 1000;
    private static final int THIRTY_SECONDS = 30 * 1000;

    static {
        HTTP_GET_SERVICE = new ThreadPoolExecutor(0, 5, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactoryBuilder()
                .setNameFormat("HTTP-GET-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());
        DOWNLOAD_SERVICE = new ThreadPoolExecutor(0, 5, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactoryBuilder()
                .setNameFormat("DOWNLOAD-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());
        CookieManager manager = new CookieManager();
        CookieHandler.setDefault(manager);
        String host = "http://91porn.com/";
    }

    public static Future<String> get(String url) {
        return HTTP_GET_SERVICE.submit(() -> {
            log.info("get  " + url.toString());
            HttpURLConnection httpURLConnection = getConnection(url);
            //todo 用代理访问？ httpURLConnection.usingProxy()
            if (httpURLConnection.getResponseCode() >= NOT_SUCCESS_RESPONSE_CODE) {
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            //todo 用buffer
            return getInputString(httpURLConnection);
        });
    }

    //todo post

    public static String post(String url, String params) throws Exception {
        HttpURLConnection connection = getConnection(url);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(params.getBytes());
        }
        if (connection.getResponseCode() >= NOT_SUCCESS_RESPONSE_CODE) {
            throw new Exception("HTTP Request is not success, Response code is " + connection.getResponseCode());
        }
        return getInputString(connection);
    }

    public static Future<String> download(String url, String filename) {
        return DOWNLOAD_SERVICE.submit(() -> {
            HttpURLConnection httpURLConnection = getDownloadConnection(url);
            if (httpURLConnection.getResponseCode() >= NOT_SUCCESS_RESPONSE_CODE) {
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            log.info("下载文件：文件名: {} 下载地址：{}", filename, url);
            try (InputStream inputStream = httpURLConnection.getInputStream()) {
                Path filePath = Paths.get(filename);
                if (Files.notExists(filePath)) {
                    Files.copy(inputStream, filePath);
                    log.info("下载成功 : {}", filePath);
                } else {
                    //todo 文件同名处理？
                    log.info("本地已存在同名文件： {}", filePath);
                }
            } catch (IOException e) {
                log.warn("文件下载失败：文件名: {} 下载地址：{}", filename, url, e);
            }
            return "fuck";
        });
    }

    private static String getInputString(HttpURLConnection connection) throws IOException {
        return new String(getInputBytes(connection));
    }

    private static byte[] getInputBytes(HttpURLConnection connection) throws IOException {
        try (InputStream inputStream = connection.getInputStream()) {
            return inputStream.readAllBytes();
        }
    }

    private static HttpURLConnection getConnection(String url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (iPad; CPU OS 11_0 like Mac OS X) AppleWebKit/604.1.34 (KHTML, like Gecko) Version/11.0 Mobile/15A5341f Safari/604.1");
        httpURLConnection.setConnectTimeout(THIRTY_SECONDS);
        httpURLConnection.setReadTimeout(THIRTY_SECONDS);
        return httpURLConnection;
    }

    private static HttpURLConnection getDownloadConnection(String url) throws IOException {
        HttpURLConnection httpURLConnection = getConnection(url);
        httpURLConnection.setReadTimeout(TEN_MINUTE);
        httpURLConnection.setConnectTimeout(TEN_MINUTE);
        return httpURLConnection;
    }
}


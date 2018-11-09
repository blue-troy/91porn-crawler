package com.bluetroy.crawler91.crawler.tools;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

import static com.bluetroy.crawler91.crawler.utils.HttpUtils.getConnection;

/**
 * @author heyixin
 */
@Log4j2
public class HttpClient {
    private static final ExecutorService HTTP_GET_SERVICE;
    private static final Integer NOT_SUCCESS_RESPONSE_CODE = 300;

    static {
        HTTP_GET_SERVICE = new ThreadPoolExecutor(0, 5, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactoryBuilder()
                .setNameFormat("HTTP-GET-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());
        String host = "http://91porn.com/";
    }

    public static Future<String> getInFuture(String url) {
        return HTTP_GET_SERVICE.submit(() -> getNow(url));
    }

    public static String getNow(String url) throws Exception {
        log.info("getNow  " + url.toString());
        HttpURLConnection httpURLConnection = getConnection(url);
        //todo 用代理访问？ httpURLConnection.usingProxy()
        if (httpURLConnection.getResponseCode() >= NOT_SUCCESS_RESPONSE_CODE) {
            throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
        }
        //todo 用buffer
        return getInputString(httpURLConnection);
    }

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

    private static String getInputString(HttpURLConnection connection) throws IOException {
        return new String(getInputBytes(connection), StandardCharsets.UTF_8);
    }

    private static byte[] getInputBytes(HttpURLConnection connection) throws IOException {
        try (InputStream inputStream = connection.getInputStream()) {
            return inputStream.readAllBytes();
        }
    }
}


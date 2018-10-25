package com.bluetroy.crawler91.crawler;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-10-24
 * Time: 6:41 AM
 */
public class Downloader {
    private static final ExecutorService DOWNLOAD_SERVICE;
    private static String url = "http://ws4.sinaimg.cn/large/6b5a0580ly1fwiddba2x1j20g81iuju9.jpg";
    private static int numberOfConcurrentThreads = 4;

    static {
        DOWNLOAD_SERVICE = new ThreadPoolExecutor(0, 5, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactoryBuilder()
                .setNameFormat("DOWNLOAD-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());
    }

    @Test
    public void downloadTest() throws IOException, InterruptedException {
        download(url);
        TimeUnit.SECONDS.sleep(1);
    }

    private void download(String url) throws IOException, InterruptedException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        if (connection.getResponseCode() == 200) {
            System.out.println(connection.getResponseCode());
            RandomAccessFile randomAccessFile = new RandomAccessFile(new File("test.jpg"), "rwd");
            int length = connection.getContentLength();
            randomAccessFile.setLength(length);
            int size = length / numberOfConcurrentThreads;
            for (int i = 0; i < numberOfConcurrentThreads; i++) {
                int startPoint = i * size;
                int endPoint = (i + 1) * size - 1;
                if (i == numberOfConcurrentThreads - 1) {
                    endPoint = length - 1;
                }
                DownloadThread(url, startPoint, endPoint);
            }
        }
    }

    private void DownloadThread(String url, int startPoint, int endPoint) {
        DOWNLOAD_SERVICE.submit(new Thread(() -> {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setReadTimeout(5000);
                connection.setRequestProperty("Range", "bytes=" + startPoint + "-" + endPoint);
                System.out.println(connection.getResponseCode());
                if (connection.getResponseCode() == 206) {
                    try (InputStream inputStream = connection.getInputStream();
                         RandomAccessFile randomAccessFile = new RandomAccessFile(new File("test.jpg"), "rw");
                    ) {
                        randomAccessFile.seek(startPoint);
                        randomAccessFile.write(inputStream.readAllBytes());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}



package com.bluetroy.crawler91.crawler.untils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

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

    public static void main(String[] args) throws IOException, InterruptedException {
        download(url);
    }

    private static void download(String url) throws IOException, InterruptedException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        File file = new File(getFileName(url));
        if (connection.getResponseCode() == 200) {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
            randomAccessFile.setLength(connection.getContentLength());
            System.out.println("文件大小理论值" + connection.getContentLength());
            CountDownLatch latch = new CountDownLatch(numberOfConcurrentThreads);
            segmentDownload(file, url, latch);
            merge(file, latch);
        }
    }

    private static void segmentDownload(File file, String url, CountDownLatch latch) {
        long fileSize = file.length();
        long segmentFileSize = fileSize / numberOfConcurrentThreads;
        for (int i = 0; i < numberOfConcurrentThreads; i++) {
            long startPoint = i * segmentFileSize;
            long endPoint = (i + 1) * segmentFileSize - 1;
            if (i == numberOfConcurrentThreads - 1) {
                endPoint = fileSize - 1;
            }
            downloadThread(url, i, startPoint, endPoint, latch);
        }
    }

    private static void merge(File file, CountDownLatch latch) throws IOException, InterruptedException {
        waitUntilSegmentDownloadFinished(latch);
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        for (File tempFile : getTempFileList(file)) {
            randomAccessFile.write(Files.readAllBytes(tempFile.toPath()));
            tempFile.delete();
        }
        System.out.println("merge success");
    }


    private static List<File> getTempFileList(File file) {
        List<File> tempFileList = new ArrayList<>();
        for (int i = 0; i < numberOfConcurrentThreads; i++) {
            tempFileList.add(new File(getTempFileName(file.toString(), i)));
        }
        return tempFileList;
    }

    private static void waitUntilSegmentDownloadFinished(CountDownLatch latch) throws InterruptedException {
        latch.await();
        System.out.println("子线程下载完毕");
    }


    private static String getFileName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    private static String getTempFileName(String url, int threadId) {
        return getFileName(url) + "." + threadId + ".temp";
    }

    private static void downloadThread(String url, int threadId, long startPoint, long endPoint, CountDownLatch latch) {
        DOWNLOAD_SERVICE.submit(() -> {
            try {
                //todo 加入已经存在文件的判断逻辑
                File file = new File(getTempFileName(url, threadId));
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setReadTimeout(5000);
                connection.setRequestProperty("Range", "bytes=" + startPoint + "-" + endPoint);
                System.out.println(connection.getResponseCode());
                if (connection.getResponseCode() == 206) {
                    try (InputStream inputStream = connection.getInputStream();
                    ) {
                        Files.copy(inputStream, file.toPath());
                        latch.countDown();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}



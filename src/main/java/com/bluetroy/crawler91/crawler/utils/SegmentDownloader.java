package com.bluetroy.crawler91.crawler.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
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
// todo 下载失败时的处理

@Log4j2
public class SegmentDownloader {
    private static final ExecutorService DOWNLOAD_SERVICE;
    private static int numberOfConcurrentThreads = 4;

    static {
        DOWNLOAD_SERVICE = new ThreadPoolExecutor(0, 5, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactoryBuilder()
                .setNameFormat("DOWNLOAD-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());
    }

    public static Future<String> downloadInFuture(String url) {
        return DOWNLOAD_SERVICE.submit(() -> {
            download(url);
            return "success";
        });
    }

    public static void download(String url) throws Exception {
        log.info("下载文件：文件名: {} 下载地址：{}", getFileNameByUrl(url), url);
        download(url, getFileNameByUrl(url), null);
    }

    private static String getFileNameByUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public static void download(String url, String fileName, Path path) throws Exception {
        HttpURLConnection connection = HttpUtils.getConnection(url);
        File file = getFile(fileName, path);
        if (Files.notExists(file.toPath())) {
            if (connection.getResponseCode() == 200) {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
                randomAccessFile.setLength(connection.getContentLength());
                CountDownLatch latch = new CountDownLatch(numberOfConcurrentThreads);
                segmentDownload(file, url, latch);
                merge(file, latch);
                log.info("下载成功 : {}", file);
            } else {
                throw new Exception("HTTP Request is not success, Response code is " + connection.getResponseCode());
            }
        } else {
            log.info("本地已存在同名文件： {}", file);
        }
    }

    private static File getFile(String fileName, Path path) {
        if (path != null) {
            return new File(path.toFile() + File.separator+ fileName);
        } else {
            return new File(fileName);
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
            downloadThread(file, url, i, startPoint, endPoint, latch);
        }
    }

    private static void merge(File file, CountDownLatch latch) throws IOException, InterruptedException {
        waitUntilSegmentDownloadFinished(latch);
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        for (File tempFile : getTempFileList(file)) {
            randomAccessFile.write(Files.readAllBytes(tempFile.toPath()));
            tempFile.delete();
        }
    }

    private static void downloadThread(File file, String url, int threadId, long startPoint, long endPoint, CountDownLatch latch) {
        DOWNLOAD_SERVICE.submit(() -> {
            try {
                File tempFile = new File(getTempFileName(file, threadId));
                if (Files.exists(tempFile.toPath()) && tempFile.length() == endPoint - startPoint) {
                    latch.countDown();
                } else {
                    HttpURLConnection connection = HttpUtils.getDownloadConnection(url);
                    connection.setRequestProperty("Range", "bytes=" + startPoint + "-" + endPoint);
                    System.out.println(connection.getResponseCode());
                    if (connection.getResponseCode() == 206) {
                        try (InputStream inputStream = connection.getInputStream()
                        ) {
                            Files.copy(inputStream, tempFile.toPath());
                        }
                    }
                }

            } catch (IOException e) {
                log.info("tempFile:{}下载失败", getTempFileName(file, threadId), e);
            } finally {
                latch.countDown();
            }
        });
    }

    private static void waitUntilSegmentDownloadFinished(CountDownLatch latch) throws InterruptedException {
        latch.await();
    }

    private static List<File> getTempFileList(File file) {
        List<File> tempFileList = new ArrayList<>();
        for (int i = 0; i < numberOfConcurrentThreads; i++) {
            tempFileList.add(new File(getTempFileName(file, i)));
        }
        return tempFileList;
    }

    private static String getTempFileName(File file, int threadId) {
        return file.getName() + "." + threadId + ".temp";
    }

    private static String getTempFileNameByUrl(String url, int threadId) {
        return getFileNameByUrl(url) + "." + threadId + ".temp";
    }
}
package com.bluetroy.crawler91.crawler;

import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-09
 * Time: 2:52 PM
 */
public interface Downloader {
    /**
     * 立刻开始，一次把ToDownloadMovies下载完毕
     */
    void downloadNow();


    /**
     * 开始进行持续下载，一旦有需要下载的视频即刻下载
     *
     * @throws InterruptedException 当获取需要下载的视频被打断时抛出异常
     */
    void startContinuousDownload() throws InterruptedException;

    /**
     * 下载对应key的视频文件
     *
     * @param key 视频文件的key
     * @return 下载结果为空，可用于判断是否下载完毕
     */
    Future downloadByKey(String key);
}

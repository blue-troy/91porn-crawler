package com.bluetroy.crawler91.crawler;

import java.nio.file.Path;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-09
 * Time: 2:52 PM
 */
interface Downloader {
    /**
     * 立刻开始，一次把ToDownloadMovies下载完毕
     */
    void downloadNow();

    /**
     * 下载对应key的视频文件
     *
     * @param key 视频文件的key
     * @return 下载结果为空，可用于判断是否下载完毕
     */
    Future downloadByKey(String key);

    /**
     * 设置下载位置
     *
     * @param path 下载位置
     */
    void setResource(Path path);
}

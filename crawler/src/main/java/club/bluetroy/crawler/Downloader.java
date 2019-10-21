package club.bluetroy.crawler;

import java.nio.file.Path;
import java.util.concurrent.Future;

/**
 * @author heyixin
 * Date: 2018-11-09
 * Time: 2:52 PM
 */
public interface Downloader {
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
     * 设置下载的并行任务及任务并发线程数
     *
     * @param parallelTask      同时下载的任务数量
     * @param concurrentThreads 任务的并发线程数
     */
    void setParallelTaskAndConcurrentThreads(Integer parallelTask, Integer concurrentThreads);

    /**
     * 设置下载位置
     *
     * @param path 下载位置
     */
    void setResource(Path path);
}

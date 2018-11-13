package com.bluetroy.crawler91.crawler.dao;

import com.bluetroy.crawler91.crawler.dao.entity.DownloadErrorInfo;
import com.bluetroy.crawler91.crawler.dao.entity.Movie;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-09
 * Time: 5:05 PM
 */
public interface BaseDao {
    /**
     * 通知dao处理这部分通过过滤的视频
     *
     * @param filteredMovies 通过过滤的视频key队列
     */
    void addFilteredMovies(Queue<String> filteredMovies);


    /**
     * 获取某个状态的视频的哈希表形式
     * @param movieStatus 视频状态
     * @return 该视频状态的视频表
     */
    ConcurrentHashMap<String, Movie> getMovies(MovieStatus movieStatus);

    Movie getMovie(String key);

    void addDownloadUrl(String key, String downloadUrl);

    void addScannedMovies(List<Movie> movies);

    void addScannedMovie(Movie movie);

    /**
     * 当过滤器被重制时应当调用这个方法
     */
    void resetFilteredStatus();

    int scannedMovieCount();

    void addDownloadError(String key);

    LinkedBlockingDeque<String> getToDownloadMovies();

    ConcurrentHashMap<String, DownloadErrorInfo> getDownloadError();

    void setDownloadedMovies(String key);
}

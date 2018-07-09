package com.bluetroy.crawler91.repository;

import com.bluetroy.crawler91.utils.TimeUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author heyixin
 * todo 闭包
 */
public class CrawlerList {
    public static final ConcurrentHashMap<String, Boolean> SCANNED_MOVIES = new ConcurrentHashMap<>();
    public static final LinkedBlockingDeque<String> TO_DOWNLOAD_MOVIES = new LinkedBlockingDeque<>();
    public static final ConcurrentHashMap<String, Boolean> FILTERED_MOVIES = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, Movie> MOVIE_DATA = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, String> DOWNLOADED_MOVIES = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, DownloadErrorInfo> DOWNLOAD_ERROR = new ConcurrentHashMap<>();

    public static void addToDownloadMoviesByKey(String k) {
        TO_DOWNLOAD_MOVIES.offer(k);
        FILTERED_MOVIES.replace(k, true);
    }

    public static void setScannedMovie(Movie movie) {
        CrawlerList.SCANNED_MOVIES.putIfAbsent(movie.getKey(), false);
        CrawlerList.MOVIE_DATA.putIfAbsent(movie.getKey(), movie);
    }

    public static void setDownloadError(String key) {
        if (DOWNLOAD_ERROR.containsKey(key)) {
            DOWNLOAD_ERROR.get(key).update();
        } else {
            DOWNLOAD_ERROR.putIfAbsent(key, new DownloadErrorInfo(key));
        }
    }

    public static void setDownloadedMovies(String key) {
        DOWNLOADED_MOVIES.putIfAbsent(key, TimeUtils.getDate());
    }
}

package com.bluetroy.crawler91.repository;

import com.bluetroy.crawler91.utils.TimeUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class CrawlerList {
    public static final ConcurrentHashMap<String, Boolean> SCANNED_MOVIES = new ConcurrentHashMap<String, Boolean>();
    public static final LinkedBlockingDeque<String> TO_DOWNLOAD_MOVIES = new LinkedBlockingDeque();
    public static final ConcurrentHashMap<String, Boolean> FILTED_MOVIES = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, Movie> MOVIE_DATA = new ConcurrentHashMap<String, Movie>();
    private static final ConcurrentHashMap<String, String> DOWNLOADED_MOVIES = new ConcurrentHashMap<String, String>();
    private static final ConcurrentHashMap<String, DownloadErrorInfo> DOWNLOAD_ERROR = new ConcurrentHashMap<String, DownloadErrorInfo>();

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
        DOWNLOADED_MOVIES.put(key, TimeUtils.getDate());
    }
}

package com.bluetroy.crawler91.repository;

import com.bluetroy.crawler91.repository.pojo.DownloadErrorInfo;
import com.bluetroy.crawler91.repository.pojo.Movie;
import com.bluetroy.crawler91.utils.TimeUtils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author heyixin
 * todo 闭包
 */
public class Repository implements Serializable {
    /**
     * 扫描下来的 movie 信息
     * key:movie的key
     * boolean:记录是否被filtered
     */
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

    public static void init(ConcurrentHashMap<String, Boolean> scannedMovies, LinkedBlockingDeque<String> toDownloadMovies, ConcurrentHashMap<String, Boolean> filteredMovies, ConcurrentHashMap<String, Movie> movieData, ConcurrentHashMap<String, String> downloadedMovies, ConcurrentHashMap<String, DownloadErrorInfo> downloadError) {
        SCANNED_MOVIES.putAll(scannedMovies);
        TO_DOWNLOAD_MOVIES.addAll(toDownloadMovies);
        FILTERED_MOVIES.putAll(filteredMovies);
        MOVIE_DATA.putAll(movieData);
        DOWNLOADED_MOVIES.putAll(downloadedMovies);
        DOWNLOAD_ERROR.putAll(downloadError);

    }

    public static void writeObject(ObjectOutputStream scannedMoviesOutputStream, ObjectOutputStream toDownloadMoviesOutputStream, ObjectOutputStream filteredMoviesOutputStream, ObjectOutputStream movieDataOutputStream, ObjectOutputStream downloadedMoviesOutputStream, ObjectOutputStream downloadErrorOutputStream) throws IOException {
        scannedMoviesOutputStream.writeObject(SCANNED_MOVIES);
        toDownloadMoviesOutputStream.writeObject(TO_DOWNLOAD_MOVIES);
        filteredMoviesOutputStream.writeObject(FILTERED_MOVIES);
        movieDataOutputStream.writeObject(MOVIE_DATA);
        downloadedMoviesOutputStream.writeObject(DOWNLOADED_MOVIES);
        downloadErrorOutputStream.writeObject(DOWNLOAD_ERROR);
        scannedMoviesOutputStream.flush();
        toDownloadMoviesOutputStream.flush();
        filteredMoviesOutputStream.flush();
        movieDataOutputStream.flush();
        downloadedMoviesOutputStream.flush();
        downloadErrorOutputStream.flush();
    }

    public static void setScannedMovie(Movie movie) {
        Repository.SCANNED_MOVIES.putIfAbsent(movie.getKey(), false);
        Repository.MOVIE_DATA.putIfAbsent(movie.getKey(), movie);
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

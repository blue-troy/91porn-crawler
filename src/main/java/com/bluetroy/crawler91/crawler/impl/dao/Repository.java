package com.bluetroy.crawler91.crawler.impl.dao;

import com.bluetroy.crawler91.crawler.impl.dao.entity.DownloadErrorInfo;
import com.bluetroy.crawler91.crawler.impl.dao.entity.Movie;
import com.bluetroy.crawler91.crawler.impl.utils.TimeUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author heyixin
 * todo 闭包
 */
@Log4j2
@Component
public class Repository implements Serializable, Persistability {

    @Autowired
    transient
    Persistence persistence;

    /**
     * 扫描下来的 movie 信息
     * key:movie的key
     * boolean:记录是否被filtered
     */
    ConcurrentHashMap<String, Boolean> scannedMovies;
    LinkedBlockingDeque<String> toDownloadMovies;
    ConcurrentHashMap<String, Boolean> filteredMovies;
    ConcurrentHashMap<String, Movie> movieData;
    ConcurrentHashMap<String, String> downloadedMovies;
    ConcurrentHashMap<String, DownloadErrorInfo> downloadError;

    public void addFilteredMovies(ConcurrentHashMap<String, Boolean> filteredMovies) {
        this.filteredMovies.putAll(filteredMovies);
    }

    public void addToDownloadMoviesByKey(String k) {
        toDownloadMovies.offer(k);
        filteredMovies.replace(k, true);
    }

    //todo 完成功能
    public HashMap<String, Movie> getMoviesData(MovieStatus movieStatus) {
        HashMap<String, Movie> hashMap = new HashMap<>();
        switch (movieStatus) {
            case TO_DOWNLOAD_MOVIES:
                toDownloadMovies.forEach(k -> {
                    hashMap.put(k, movieData.get(k));
                });
                break;
            default:
                getMap(movieStatus).forEachKey(1, k -> {
                    hashMap.put((String) k, movieData.get((String) k));
                });
        }

        return hashMap;
    }

    private ConcurrentHashMap getMap(MovieStatus movieStatus) {
        switch (movieStatus) {
            case SCANNED_MOVIES:
                return scannedMovies;
            case FILTERED_MOVIES:
                return filteredMovies;
            case DOWNLOAD_ERROR:
                return downloadError;
            case DOWNLOADED_MOVIES:
                return downloadedMovies;
            default:
        }
        return (ConcurrentHashMap) Collections.EMPTY_MAP;
    }

    @Override
    public void init(Repository repository) {
        persistence.init(this);
    }

    @Override
    public void save(Serializable serializable) {
        persistence.save(this);
    }

    public void init() {
        init(this);
    }

    @PreDestroy
    public void save() {
        save(this);
    }

    public Movie getMovieData(String key) {
        return getMovieData().get(key);
    }

    public ConcurrentHashMap<String, Boolean> getScannedMovies() {
        return scannedMovies;
    }

    public LinkedBlockingDeque<String> getToDownloadMovies() {
        return toDownloadMovies;
    }

    public ConcurrentHashMap<String, Boolean> getFilteredMovies() {
        return filteredMovies;
    }

    public ConcurrentHashMap<String, Movie> getMovieData() {
        return movieData;
    }

    public ConcurrentHashMap<String, String> getDownloadedMovies() {
        return downloadedMovies;
    }

    public void setDownloadedMovies(String key) {
        downloadedMovies.putIfAbsent(key, TimeUtils.getDate());
    }

    public ConcurrentHashMap<String, DownloadErrorInfo> getDownloadError() {
        return downloadError;
    }

    public void setDownloadError(String key) {
        if (downloadError.containsKey(key)) {
            downloadError.get(key).update();
        } else {
            downloadError.putIfAbsent(key, new DownloadErrorInfo(key));
        }
    }

    public ConcurrentHashMap<String, Boolean> getTobeFilter() {
        //todo 根据 scannedMovies 设置初始值
        ConcurrentHashMap<String, Boolean> tobeFilter = new ConcurrentHashMap<>(16);
        getScannedMovies().forEach(5, (k, v) -> {
            if (v) {
                return;
            }
            tobeFilter.put(k, false);
            scannedMovies.replace(k, true);
        });
        return tobeFilter;
    }

    public HashMap<String, Movie> getFilteredMoviesMap() {
        HashMap<String, Movie> hashMap = new HashMap<>();
        filteredMovies.forEachKey(1, k -> {
            hashMap.put(k, movieData.get(k));
        });
        return hashMap;
    }

    /**
     * 设置扫描到的视频
     * 若视频曾经被扫描过，且有所不同则应该去更新视频的信息
     * 没有不同就算了。
     * 若不被扫描过，则应当去增加视频的信息。
     *
     * @param movie
     */
    public void setScannedMovie(Movie movie) {
        if (scannedMovies.containsKey(movie.getKey())) {
            if (movie.compareTo(movieData.get(movie.getKey())) != 0) {
                movieData.get(movie.getKey()).update(movie);
                scannedMovies.replace(movie.getKey(), false);
                movieData.get(movie.getKey()).update(movie);
            }
        } else {
            scannedMovies.putIfAbsent(movie.getKey(), false);
            movieData.put(movie.getKey(), movie);
        }
    }
}

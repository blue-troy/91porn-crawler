package com.bluetroy.crawler91.crawler.dao;

import com.bluetroy.crawler91.crawler.dao.entity.DownloadErrorInfo;
import com.bluetroy.crawler91.crawler.dao.entity.Movie;

import java.util.HashMap;
import java.util.List;
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
    void addFilteredMovies(ConcurrentHashMap<String, Boolean> filteredMovies);

    void addToDownloadMoviesByKey(String k);

    HashMap<String, Movie> getMoviesData(MovieStatus movieStatus);

    void init();

    Movie getMovieData(String key);

    void addDownloadUrl(String key, String downloadUrl);

    void addScannedMovie(List<Movie> movies);

    ConcurrentHashMap<String, Boolean> getScannedMovies();

    LinkedBlockingDeque<String> getToDownloadMovies();

    ConcurrentHashMap<String, Movie> getMovieData();

    ConcurrentHashMap<String, String> getDownloadedMovies();

    void setDownloadedMovies(String key);

    ConcurrentHashMap<String, DownloadErrorInfo> getDownloadError();

    void setDownloadError(String key);

    ConcurrentHashMap<String, Boolean> getTobeFilter();

    HashMap<String, Movie> getFilteredMoviesMap();

    void addScannedMovie(Movie movie);
}

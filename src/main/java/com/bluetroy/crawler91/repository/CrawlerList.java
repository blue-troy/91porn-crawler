package com.bluetroy.crawler91.repository;

import java.util.concurrent.ConcurrentHashMap;

public class CrawlerList {
    public static final ConcurrentHashMap<String, Movie> ScannedMovieList = new ConcurrentHashMap<String, Movie>();
    public static final ConcurrentHashMap<String, Movie> FilterMovieList = new ConcurrentHashMap<String, Movie>();
    public static final ConcurrentHashMap<String, Movie> DownloadedMovieList = new ConcurrentHashMap<String, Movie>();
    public static final ConcurrentHashMap<String, Movie> ToDownloadMovieList = new ConcurrentHashMap<String, Movie>();
}

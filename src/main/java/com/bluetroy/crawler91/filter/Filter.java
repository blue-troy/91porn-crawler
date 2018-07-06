package com.bluetroy.crawler91.filter;

import com.bluetroy.crawler91.repository.Movie;

import java.util.concurrent.ConcurrentHashMap;

public interface Filter {
    public abstract void doFilter(ConcurrentHashMap<String, Movie> before, ConcurrentHashMap<String, Movie> after, FilterChain FilterChain);
}

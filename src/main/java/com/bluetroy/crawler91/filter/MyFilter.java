package com.bluetroy.crawler91.filter;

import com.bluetroy.crawler91.repository.Movie;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyFilter {
    public boolean filter(ConcurrentHashMap<String, Movie> before, ConcurrentHashMap<String, Movie> after) {
        for (Map.Entry<String, Movie> stringMovieEntry : before.entrySet()) {
            if (true) after.put(stringMovieEntry.getKey(), stringMovieEntry.getValue());
        }
        return true;
    }
}
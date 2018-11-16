package com.bluetroy.crawler91.crawler.filter.impl;

import com.bluetroy.crawler91.crawler.dao.BaseDao;
import com.bluetroy.crawler91.crawler.dao.entity.Movie;
import com.bluetroy.crawler91.crawler.filter.MovieFilter;
import com.bluetroy.crawler91.crawler.utils.ApplicationContextProvider;
import lombok.ToString;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyixin
 */
@ToString
public class CollectMovieFilter implements MovieFilter {
    private final Integer collectNum;

    public CollectMovieFilter(Integer collectNum) {
        this.collectNum = collectNum;
    }

    @Override
    public void doFilter(ConcurrentHashMap<String, Movie> tobeFilter) {
        tobeFilter.forEach(5, (k, v) -> {
            if (v.getCollect() >= collectNum) {
                return;
            }
            tobeFilter.remove(k);
        });
    }
}

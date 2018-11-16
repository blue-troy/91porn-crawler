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
public class TitleMovieFilter implements MovieFilter {
    private String keyword;

    public TitleMovieFilter(String keyword) {
        this.keyword = keyword;
    }

    private TitleMovieFilter() {
    }

    @Override
    public void doFilter(ConcurrentHashMap<String, Movie> tobeFilter) {
        tobeFilter.forEach(5, (k, v) -> {
            if (v.getTitle().contains(keyword)) {
                return;
            }
            tobeFilter.remove(k);
        });
    }

}

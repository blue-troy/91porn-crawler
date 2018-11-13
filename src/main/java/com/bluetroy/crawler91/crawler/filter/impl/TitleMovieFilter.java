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
    @ToString.Exclude
    private BaseDao dao;
    private String keyword;

    public TitleMovieFilter(String keyword) {
        this.keyword = keyword;
        dao = ApplicationContextProvider.getBean("persistentDao", BaseDao.class);
    }

    private TitleMovieFilter() {
    }

    @Override
    public void doFilter(ConcurrentHashMap<String, Movie> tobeFilter) {
        tobeFilter.forEach(5, (k, v) -> {
            if (dao.getMovie(k).getTitle().contains(keyword)) {
                return;
            }
            tobeFilter.remove(k);
        });
    }

}

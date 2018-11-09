package com.bluetroy.crawler91.crawler.filter.impl;

import com.bluetroy.crawler91.crawler.dao.BaseDao;
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
    @ToString.Exclude
    private BaseDao dao;


    public CollectMovieFilter(Integer collectNum) {
        this.collectNum = collectNum;
        dao = ApplicationContextProvider.getBean("persistentDao", BaseDao.class);
    }

    @Override
    public void doFilter(ConcurrentHashMap<String, Boolean> tobeFilter) {
        tobeFilter.forEach(5, (k, v) -> {
            if (dao.getMovieData().get(k).getCollect() >= collectNum) {
                return;
            }
            tobeFilter.remove(k);
        });
    }
}

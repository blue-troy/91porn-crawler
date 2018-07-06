package com.bluetroy.crawler91.crawler.filter.impl.filter;

import com.bluetroy.crawler91.crawler.filter.Filter;

import java.util.concurrent.ConcurrentHashMap;

import static com.bluetroy.crawler91.repository.CrawlerList.MOVIE_DATA;

public class CollectFilter implements Filter {
    private Integer collectNum = 0;

    public CollectFilter(Integer collectNum) {
        this.collectNum = collectNum;
    }

    @Override
    public void doFilter(ConcurrentHashMap<String, Boolean> tobeFilter) {
        tobeFilter.forEach(5, (k, v) -> {
            if (MOVIE_DATA.get(k).getCollect() >= collectNum) return;
            tobeFilter.remove(k);
        });
    }
}

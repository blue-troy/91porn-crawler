package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.crawler.filter.impl.FilterChainFactory;

import java.util.concurrent.ConcurrentHashMap;

import static com.bluetroy.crawler91.repository.CrawlerList.FILTED_MOVIES;
import static com.bluetroy.crawler91.repository.CrawlerList.SCANNED_MOVIES;

public class Filter {
    public void doFilter() {
        ConcurrentHashMap<String, Boolean> tobeFilter = getTobeFilterMap();
        FilterChainFactory.getShowFaceCollectFilterChain(10).doFilter(tobeFilter);
        FILTED_MOVIES.putAll(tobeFilter);
    }

    private ConcurrentHashMap getTobeFilterMap() {
        ConcurrentHashMap<String, Boolean> tobeFilter = new ConcurrentHashMap<>();
        SCANNED_MOVIES.forEach(5, (k, v) -> {
            if (v) return;
            tobeFilter.put(k, false);
            SCANNED_MOVIES.replace(k, true);
        });
        return tobeFilter;
    }
}

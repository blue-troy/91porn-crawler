package com.bluetroy.crawler91.crawler.filter.impl.filter;

import com.bluetroy.crawler91.crawler.filter.Filter;

import java.util.concurrent.ConcurrentHashMap;

import static com.bluetroy.crawler91.repository.CrawlerList.MOVIE_DATA;

public class ShowFaceFilter implements Filter {
    public void doFilter(ConcurrentHashMap<String, Boolean> tobeFilter) {
        tobeFilter.forEach(5, (k, v) -> {
            if (MOVIE_DATA.get(k).getTitle().contains("露脸")) return;
            tobeFilter.remove(k);
        });
    }
}

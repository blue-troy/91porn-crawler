package com.bluetroy.crawler91.crawler.filter.impl.filter;

import com.bluetroy.crawler91.crawler.filter.Filter;

import java.util.concurrent.ConcurrentHashMap;

import static com.bluetroy.crawler91.repository.CrawlerList.MOVIE_DATA;

/**
 * @author heyixin
 */
public class TitleFilter implements Filter {
    private String keyword;

    public TitleFilter(String keyword) {
        this.keyword = keyword;
    }

    private TitleFilter() {
    }

    @Override
    public void doFilter(ConcurrentHashMap<String, Boolean> tobeFilter) {
        tobeFilter.forEach(5, (k, v) -> {
            if (MOVIE_DATA.get(k).getTitle().contains(keyword)) {
                return;
            }
            tobeFilter.remove(k);
        });
    }
}

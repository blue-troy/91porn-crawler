package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.crawler.filter.impl.FilterChainFactory;
import com.bluetroy.crawler91.repository.CrawlerList;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

import static com.bluetroy.crawler91.repository.CrawlerList.FILTERED_MOVIES;
import static com.bluetroy.crawler91.repository.CrawlerList.SCANNED_MOVIES;

/**
 * @author heyixin
 */
@Component
@Log4j2
public class Filter {
    public void doFilter() {
        ConcurrentHashMap<String, Boolean> tobeFilter = getTobeFilterMap();
        FilterChainFactory.getShowFaceCollectFilterChain(10).doFilter(tobeFilter);
        tobeFilter.forEachKey(1, k -> {
            log.info(CrawlerList.MOVIE_DATA.get(k).toString());
        });
        FILTERED_MOVIES.putAll(tobeFilter);
    }


    private ConcurrentHashMap<String, Boolean> getTobeFilterMap() {
        //todo 根据 SCANNED_MOVIES 设置初始值
        ConcurrentHashMap<String, Boolean> tobeFilter = new ConcurrentHashMap<>(16);
        SCANNED_MOVIES.forEach(5, (k, v) -> {
            if (v) {
                return;
            }
            tobeFilter.put(k, false);
            SCANNED_MOVIES.replace(k, true);
        });
        return tobeFilter;
    }
}

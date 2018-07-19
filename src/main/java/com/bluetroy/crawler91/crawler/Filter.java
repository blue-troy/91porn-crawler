package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.crawler.filter.FilterChain;
import com.bluetroy.crawler91.crawler.filter.impl.FilterChainFactory;
import com.bluetroy.crawler91.dao.Repository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

import static com.bluetroy.crawler91.dao.Repository.getTobeFilter;

/**
 * @author heyixin
 */
@Component
@Log4j2
public class Filter {
    public void doFilter() {
        ConcurrentHashMap<String, Boolean> tobeFilter = getTobeFilter();
        FilterChainFactory.getShowFaceCollectFilterChain(200).doFilter(tobeFilter);
        tobeFilter.forEachKey(1, k -> {
            log.info(Repository.getMovieData().get(k).toString());
        });
        Repository.addFilteredMovies(tobeFilter);
    }

    //todo filter新功能

    public void changeFilter(FilterChain filterChain) {

    }

    public void addFilter(FilterChain filterChain) {

    }

    public void addOrFilter(FilterChain filterChain) {

    }
}

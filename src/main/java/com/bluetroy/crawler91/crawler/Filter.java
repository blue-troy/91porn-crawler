package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.crawler.filter.FilterChain;
import com.bluetroy.crawler91.crawler.filter.impl.FilterChainFactory;
import com.bluetroy.crawler91.dao.Repository;
import com.bluetroy.crawler91.vo.FilterVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

import static com.bluetroy.crawler91.dao.Repository.getMovieData;
import static com.bluetroy.crawler91.dao.Repository.getTobeFilter;

/**
 * @author heyixin
 */
@Component()
@Log4j2
public class Filter {
    private FilterChain filterChain = FilterChainFactory.getShowFaceCollectFilterChain(200);

    public void doFilter() {
        ConcurrentHashMap<String, Boolean> tobeFilter = getTobeFilter();
        getFilterChain().doFilter(tobeFilter);
        tobeFilter.forEachKey(1, k -> {
            log.info(Repository.getMovieData().get(k).toString());
        });
        Repository.addFilteredMovies(tobeFilter);
    }

    public void changeFilter(FilterChain filterChain) {

    }

    //todo filter新功能

    public void addFilter(FilterChain filterChain) {

    }

    public void addOrFilter(FilterChain filterChain) {

    }

    public String getFilterInfo() {
        return this.filterChain.toString();
    }

    private FilterChain getFilterChain() {
        return this.filterChain;
    }

    public void setFilter(FilterVO filterVO) {
        this.filterChain = FilterChainFactory.getFilter(filterVO);
    }
}

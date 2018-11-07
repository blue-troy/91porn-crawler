package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.crawler.dao.Repository;
import com.bluetroy.crawler91.crawler.filter.FilterChain;
import com.bluetroy.crawler91.crawler.filter.FilterUpdateListener;
import com.bluetroy.crawler91.crawler.filter.impl.FilterChainFactory;
import com.bluetroy.crawler91.vo.FilterVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyixin
 */
@Component()
@Log4j2
public class Filter {
    @Autowired
    private FilterUpdateListener filterUpdateListener;
    @Autowired
    private Repository repository;
    private FilterChain filterChain;

    public void doFilter() {
        log.info("doing filter. filterChain : " + getFilterInfo());
        ConcurrentHashMap<String, Boolean> tobeFilter = repository.getTobeFilter();
        getFilterChain().doFilter(tobeFilter);
        tobeFilter.forEachKey(1, k -> {
            log.info(repository.getMovieData().get(k).toString());
        });
        repository.addFilteredMovies(tobeFilter);
    }

    //todo filter新功能

    public String getFilterInfo() {
        return getFilterChain().toString();
    }

    private FilterChain getFilterChain() {
        if (filterChain == null) {
            filterChain = FilterChainFactory.getShowFaceCollectFilterChain(200);
        }
        return this.filterChain;
    }

    public void setFilter(FilterVO filterVO) {
        FilterChain newFilter = FilterChainFactory.getFilter(filterVO);
        if (!newFilter.equals(this.filterChain)) {
            this.filterChain = newFilter;
            filterUpdateListener.update(filterVO);
        }
    }
}

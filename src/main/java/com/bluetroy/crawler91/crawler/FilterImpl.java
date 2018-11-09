package com.bluetroy.crawler91.crawler;

import com.bluetroy.crawler91.crawler.dao.BaseDao;
import com.bluetroy.crawler91.crawler.filter.FilterUpdateListener;
import com.bluetroy.crawler91.crawler.filter.MovieFilterChain;
import com.bluetroy.crawler91.crawler.filter.impl.FilterChainFactory;
import com.bluetroy.crawler91.vo.FilterVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyixin
 */
@Log4j2
@Service("filter")
class FilterImpl implements Filter {
    @Autowired
    private FilterUpdateListener filterUpdateListener;
    @Autowired
    private BaseDao dao;
    private MovieFilterChain filterChain;

    @Override
    public void doFilter() {
        log.info("doing filterr. filterChain : " + getFilterInfo());
        ConcurrentHashMap<String, Boolean> tobeFilter = dao.getTobeFilter();
        getFilterChain().doFilter(tobeFilter);
        tobeFilter.forEachKey(1, k -> {
            log.info(dao.getMovieData().get(k).toString());
        });
        dao.addFilteredMovies(tobeFilter);
    }

    @Override
    public String getFilterInfo() {
        return getFilterChain().toString();
    }

    private MovieFilterChain getFilterChain() {
        if (filterChain == null) {
            filterChain = FilterChainFactory.getShowFaceCollectFilterChain(200);
        }
        return this.filterChain;
    }

    @Override
    public void setFilter(FilterVO filterVO) {
        MovieFilterChain newFilter = FilterChainFactory.getFilter(filterVO);
        if (!newFilter.equals(this.filterChain)) {
            this.filterChain = newFilter;
            filterUpdateListener.update(filterVO);
        }
    }
}

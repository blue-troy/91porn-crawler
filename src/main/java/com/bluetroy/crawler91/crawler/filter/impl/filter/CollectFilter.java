package com.bluetroy.crawler91.crawler.filter.impl.filter;

import com.bluetroy.crawler91.crawler.dao.Repository;
import com.bluetroy.crawler91.crawler.filter.Filter;
import com.bluetroy.crawler91.crawler.filter.FilterChain;
import com.bluetroy.crawler91.crawler.utils.ApplicationContextProvider;
import lombok.ToString;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyixin
 */
@ToString
public class CollectFilter implements Filter {
    private final Integer collectNum;
    @ToString.Exclude
    private Repository repository;


    public CollectFilter(Integer collectNum) {
        this.collectNum = collectNum;
        repository = ApplicationContextProvider.getBean("repository", Repository.class);
    }

    @Override
    public void doFilter(ConcurrentHashMap<String, Boolean> tobeFilter) {
        tobeFilter.forEach(5, (k, v) -> {
            if (repository.getMovieData().get(k).getCollect() >= collectNum) {
                return;
            }
            tobeFilter.remove(k);
        });
    }
}

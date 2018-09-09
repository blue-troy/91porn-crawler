package com.bluetroy.crawler91.crawler.filter.impl.filter;

import com.bluetroy.crawler91.crawler.filter.Filter;
import com.bluetroy.crawler91.dao.Repository;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyixin
 */
@ToString
public class CollectFilter implements Filter {
    private final Integer collectNum;
    @Autowired
    transient Repository repository;


    public CollectFilter(Integer collectNum) {
        this.collectNum = collectNum;
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

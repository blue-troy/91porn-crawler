package com.bluetroy.crawler91.crawler.filter.impl.filter;

import com.bluetroy.crawler91.crawler.filter.Filter;
import com.bluetroy.crawler91.dao.Repository;
import lombok.ToString;

import java.util.concurrent.ConcurrentHashMap;

import static com.bluetroy.crawler91.dao.Repository.MOVIE_DATA;

/**
 * @author heyixin
 */
@ToString
public class CollectFilter implements Filter {
    private final Integer collectNum;


    public CollectFilter(Integer collectNum) {
        this.collectNum = collectNum;
    }

    @Override
    public void doFilter(ConcurrentHashMap<String, Boolean> tobeFilter) {
        tobeFilter.forEach(5, (k, v) -> {
            if (Repository.getMovieData().get(k).getCollect() >= collectNum) {
                return;
            }
            tobeFilter.remove(k);
        });
    }
}

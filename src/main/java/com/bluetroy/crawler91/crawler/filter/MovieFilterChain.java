package com.bluetroy.crawler91.crawler.filter;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyixin
 */
@Log4j2
@ToString
public class MovieFilterChain implements MovieFilter {
    private final ArrayList<MovieFilter> movieFilterList = new ArrayList<>();


    public MovieFilterChain addFilter(MovieFilter movieFilter) {
        movieFilterList.add(movieFilter);
        return this;
    }

    @Override
    public void doFilter(ConcurrentHashMap<String, Boolean> tobeFilter) {
        log.info("当前责任链为：{}", this::toString);
        for (MovieFilter movieFilter : movieFilterList) {
            movieFilter.doFilter(tobeFilter);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MovieFilterChain chain = (MovieFilterChain) o;
        return chain.toString().equals(this.toString());
    }

}

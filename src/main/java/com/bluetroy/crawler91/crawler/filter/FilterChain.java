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
public class FilterChain implements Filter {
    private final ArrayList<Filter> filterList = new ArrayList<>();


    public FilterChain addFilter(Filter filter) {
        filterList.add(filter);
        return this;
    }

    @Override
    public void doFilter(ConcurrentHashMap<String, Boolean> tobeFilter) {
        log.info("当前责任链为：{}", this::toString);
        for (Filter filter : filterList) {
            filter.doFilter(tobeFilter);
        }
    }
}

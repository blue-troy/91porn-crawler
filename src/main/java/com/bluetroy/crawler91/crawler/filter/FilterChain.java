package com.bluetroy.crawler91.crawler.filter;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyixin
 */
public class FilterChain implements Filter {
    private final ArrayList<Filter> filterList = new ArrayList<>();


    public FilterChain addFilter(Filter filter) {
        filterList.add(filter);
        return this;
    }

    @Override
    public void doFilter(ConcurrentHashMap<String, Boolean> tobeFilter) {
        for (Filter filter : filterList) {
            filter.doFilter(tobeFilter);
        }
    }
}

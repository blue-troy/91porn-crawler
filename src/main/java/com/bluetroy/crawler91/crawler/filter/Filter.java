package com.bluetroy.crawler91.crawler.filter;

import java.util.concurrent.ConcurrentHashMap;

public interface Filter {
    public abstract void doFilter(ConcurrentHashMap<String, Boolean> tobeFilter);
}
